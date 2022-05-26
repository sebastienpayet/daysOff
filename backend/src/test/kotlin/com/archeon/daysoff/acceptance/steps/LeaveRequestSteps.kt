package com.archeon.daysoff.acceptance.steps

import com.archeon.daysoff.acceptance.SharedData
import com.archeon.daysoff.business.domain.leave.LeaveRequest
import com.archeon.daysoff.business.domain.leave.LeaveType
import com.archeon.daysoff.business.port.generator.IdGenerator
import com.archeon.daysoff.business.port.repository.LeaveRepository
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.service.LeaveRequestService
import com.archeon.daysoff.business.useCase.leave.createALeaveRequest.CreateALeaveRequest
import com.archeon.daysoff.business.useCase.leave.createALeaveRequest.CreateALeaveRequestCommand
import com.archeon.daysoff.business.useCase.leave.submitALeaveRequest.SubmitALeaveRequest
import com.archeon.daysoff.business.useCase.leave.submitALeaveRequest.SubmitALeaveRequestCommand
import com.archeon.daysoff.business.useCase.leave.updateALeaveRequest.UpdateALeaveRequest
import com.archeon.daysoff.business.useCase.leave.updateALeaveRequest.UpdateALeaveRequestCommand
import com.archeon.daysoff.business.useCase.leave.validateALeaveRequestByManagement.ValidateALeaveRequestByManagement
import com.archeon.daysoff.business.useCase.leave.validateALeaveRequestByManagement.ValidateALeaveRequestByManagementCommand
import com.archeon.daysoff.business.useCase.leave.validateALeaveRequestByService.ValidateALeaveRequestByService
import com.archeon.daysoff.business.useCase.leave.validateALeaveRequestByService.ValidateALeaveRequestByServiceCommand
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_LEAVE
import com.archeon.daysoff.business.util.ErrorUtil.UNKNOWN_USER
import io.cucumber.java8.En
import io.cucumber.java8.Scenario
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.test.assertEquals

class LeaveRequestSteps(
    private val sharedData: SharedData,
    private val userRepository: UserRepository,
    private val createALeaveRequest: CreateALeaveRequest,
    private val leaveRepository: LeaveRepository,
    private val leaveRequestService: LeaveRequestService,
    private val submitALeaveRequest: SubmitALeaveRequest,
    private val updateALeaveRequest: UpdateALeaveRequest,
    private val idGenerator: IdGenerator,
    private val validateALeaveRequestByService: ValidateALeaveRequestByService,
    private val validateALeaveRequestByManagement: ValidateALeaveRequestByManagement
) : En {
    init {
        When("^je crée une demande congés pour \"([^']*)\" \"([^']*)\" du \"([^']*)\" à \"([^']*)\" de type \"([^']*)\"$") { lastname: String, firstname: String, startDate: String, endDate: String, type: String ->
            val user = userRepository.findByFirstnameAndLastname(firstname, lastname) ?: error(UNKNOWN_USER)
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val command = CreateALeaveRequestCommand(
                startDate = LocalDate.parse(startDate, formatter),
                endDate = LocalDate.parse(endDate, formatter),
                leaveType = LeaveType.valueOf(type),
                userId = user.id ?: "noId",
                comment = ""
            )

            try {
                createALeaveRequest.handle(command)
            } catch (e: IllegalArgumentException) {
                sharedData.currentException = e
            }
        }

        When("^il existe une demande congés pour \"([^']*)\" \"([^']*)\" du \"([^']*)\" à \"([^']*)\" de type \"([^']*)\"$") { lastname: String, firstname: String, startDate: String, endDate: String, type: String ->
            val user = userRepository.findByFirstnameAndLastname(firstname, lastname) ?: error(UNKNOWN_USER)
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val leaveRequest = LeaveRequest(
                depositDate = Instant.now(),
                startDate = LocalDate.parse(startDate, formatter),
                endDate = LocalDate.parse(endDate, formatter),
                leaveType = LeaveType.valueOf(type),
                userId = user.id ?: "noId",
                comment = "",
                fullDayAtStart = false,
                fullDayAtEnd = false,
                leaveWorkflows = emptySet(),
                id = idGenerator.generate()
            )
            leaveRepository.save(leaveRequest)
        }

        Then("^la demande congés pour \"([^']*)\" \"([^']*)\" du \"([^']*)\" à \"([^']*)\" de type \"([^']*)\" existe pour une durée de \"([^']*)\"$") { lastname: String, firstname: String, startDate: String, endDate: String, type: String, duration: BigDecimal ->
            val user = userRepository.findByFirstnameAndLastname(firstname, lastname) ?: error(UNKNOWN_USER)
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val result = leaveRepository.findAll().filter {
                (it.startDate == LocalDate.parse(startDate, formatter)
                        && it.endDate == LocalDate.parse(endDate, formatter)
                        && it.leaveType == LeaveType.valueOf(type)
                        && it.userId == (user.id ?: "noId")
                        )
            }

            assertEquals(1, result.size)
            assertEquals(duration, leaveRequestService.computeLeaveDuration(result.first()))
        }

        Then("^la demande congés pour \"([^']*)\" \"([^']*)\" du \"([^']*)\" à \"([^']*)\" de type \"([^']*)\" existe$") { lastname: String, firstname: String, startDate: String, endDate: String, type: String ->
            val user = userRepository.findByFirstnameAndLastname(firstname, lastname) ?: error(UNKNOWN_USER)
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val result = leaveRepository.findAll().filter {
                (it.startDate == LocalDate.parse(startDate, formatter)
                        && it.endDate == LocalDate.parse(endDate, formatter)
                        && it.leaveType == LeaveType.valueOf(type)
                        && it.userId == (user.id ?: "noId")
                        )
            }

            assertEquals(1, result.size)
        }

        When("^je soumet la demande congés pour \"([^']*)\" \"([^']*)\" du \"([^']*)\" à \"([^']*)\" de type \"([^']*)\" pour validation$") { lastname: String, firstname: String, startDate: String, endDate: String, type: String ->
            try {
                Thread.sleep(1)
                val user = userRepository.findByFirstnameAndLastname(firstname, lastname) ?: error(UNKNOWN_USER)
                val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                val result = leaveRepository.findAll().first {
                    (it.startDate == LocalDate.parse(startDate, formatter)
                            && it.endDate == LocalDate.parse(endDate, formatter)
                            && it.leaveType == LeaveType.valueOf(type)
                            && it.userId == (user.id ?: "noId")
                            )
                }

                val command = SubmitALeaveRequestCommand(
                    leaveId = result.id ?: error(UNKNOWN_LEAVE),
                    comment = ""
                )
                submitALeaveRequest.handle(command)
            } catch (e: Exception) {
                sharedData.currentException = e
            }
        }
        When("^je modifie une demande congés pour \"([^']*)\" \"([^']*)\" de \"([^']*)\" à \"([^']*)\" de type \"([^']*)\" vers \"([^']*)\" à \"([^']*)\" de type \"([^']*)\"$") { lastname: String, firstname: String, beforeStartDate: String, beforeEndDate: String, beforeType: String, afterStartDate: String, afterEndDate: String, afterType: String ->
            val user = userRepository.findByFirstnameAndLastname(firstname, lastname) ?: error(UNKNOWN_USER)
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val result = leaveRepository.findAll().first {
                (it.startDate == LocalDate.parse(beforeStartDate, formatter)
                        && it.endDate == LocalDate.parse(beforeEndDate, formatter)
                        && it.leaveType == LeaveType.valueOf(beforeType)
                        && it.userId == (user.id ?: "noId")
                        )
            }

            val command = UpdateALeaveRequestCommand(
                leaveId = result.id ?: error("noId"),
                startDate = LocalDate.parse(afterStartDate, formatter),
                endDate = LocalDate.parse(afterEndDate, formatter),
                leaveType = LeaveType.valueOf(afterType),
                comment = ""
            )

            try {
                updateALeaveRequest.handle(command)
            } catch (e: IllegalArgumentException) {
                sharedData.currentException = e
            }
        }

        When("^je valide par le service la demande congés pour \"([^']*)\" \"([^']*)\" du \"([^']*)\" à \"([^']*)\" de type \"([^']*)\"$") { lastname: String, firstname: String, startDate: String, endDate: String, type: String ->
            val user = userRepository.findByFirstnameAndLastname(firstname, lastname) ?: error(UNKNOWN_USER)
            val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
            val result = leaveRepository.findAll().first {
                (it.startDate == LocalDate.parse(startDate, formatter)
                        && it.endDate == LocalDate.parse(endDate, formatter)
                        && it.leaveType == LeaveType.valueOf(type)
                        && it.userId == (user.id ?: "noId")
                        )
            }

            val command = ValidateALeaveRequestByServiceCommand(
                leaveId = result.id ?: error(UNKNOWN_LEAVE),
                comment = ""
            )

            try {
                validateALeaveRequestByService.handle(command)
            } catch (e: IllegalArgumentException) {
                sharedData.currentException = e
            }
        }

        When("^je valide définitivement la demande congés avec la répartition annual: \"([^']*)\", recovery: \"([^']*)\", special: \"([^']*)\", WTR: \"([^']*)\", TSA: \"([^']*)\"$") { annualBalance: BigDecimal, recoveryBalance: BigDecimal, specialBalance: BigDecimal, WTRBalance: BigDecimal, TSABalance: BigDecimal ->
            val currentLeaveRequest = sharedData.domainDatas.first() as LeaveRequest

            val command = ValidateALeaveRequestByManagementCommand(
                leaveId = currentLeaveRequest.id ?: error(UNKNOWN_LEAVE),
                comment = "",
                annual = annualBalance,
                special = specialBalance,
                recovery = recoveryBalance,
                timeSavingAccount = TSABalance,
                workingTimeReduction = WTRBalance
            )

            try {
                validateALeaveRequestByManagement.handle(command)
            } catch (e: IllegalArgumentException) {
                sharedData.currentException = e
            }
        }
    }
}

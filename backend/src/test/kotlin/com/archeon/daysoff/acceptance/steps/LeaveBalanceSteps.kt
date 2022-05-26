package com.archeon.daysoff.acceptance.steps

import com.archeon.daysoff.acceptance.SharedData
import com.archeon.daysoff.business.domain.leave.BalanceType
import com.archeon.daysoff.business.domain.leave.LeaveType
import com.archeon.daysoff.business.port.repository.UserRepository
import com.archeon.daysoff.business.useCase.leaveBalance.createALeaveBalance.CreateALeaveBalance
import com.archeon.daysoff.business.useCase.leaveBalance.createALeaveBalance.CreateALeaveBalanceCommand
import com.archeon.daysoff.business.useCase.leaveBalance.listLeaveBalances.ListLeaveBalancesCommand
import com.archeon.daysoff.business.useCase.leaveBalance.listLeaveBalances.ListLeaveBalances
import io.cucumber.java8.En
import java.math.BigDecimal
import kotlin.test.assertEquals

class LeaveBalanceSteps(
    private val sharedData: SharedData,
    private val userRepository: UserRepository,
    private val createALeaveBalance: CreateALeaveBalance,
    private val listLeaveBalances: ListLeaveBalances
) : En {
	init {
		When("^je crée un solde de congé de type '([^']*)' pour l'année '([^']*)', d'un montant de '([^']*)' pour l'usager '([^']*)' '([^']*)'$") {
				type: String, year: Int, amount: BigDecimal, lastname: String, firstname: String ->

			val currentUser = userRepository.findAll().first { it.lastname == lastname && it.firstname == firstname }

			val command = CreateALeaveBalanceCommand(
				leaveId = null,
				userId = currentUser.id.toString(),
				leaveType = type,
				balanceType = BalanceType.CREDIT.toString(),
				year = year,
				amount = amount,
				comment = "my comment"
			)

			try {
				createALeaveBalance.handle(command)
			} catch (e: IllegalArgumentException) {
				sharedData.currentException = e
			}
		}

		Then("^le solde de congé de type '([^']*)' pour l'année '([^']*)', d'un montant de '([^']*)' pour l'usager '([^']*)' '([^']*)' existe$") {
				type: String, year: Int, amount: BigDecimal, lastname: String, firstname: String ->
			val currentUser = userRepository.findAll().first { it.lastname == lastname && it.firstname == firstname }
			val listLeaveBalancesCommand = ListLeaveBalancesCommand(setOf(year))
			val leaveBalances = listLeaveBalances.handle(listLeaveBalancesCommand)

			leaveBalances.first { leaveBalance ->
				leaveBalance.userId == currentUser.id
						&& leaveBalance.leaveType == LeaveType.valueOf(type)
						&& leaveBalance.balanceType == BalanceType.CREDIT
						&& leaveBalance.amount == amount
						&& leaveBalance.year == year
			}
		}

		And("^il n'existe qu'un solde de type '([^']*)' pour l'année '(\\d{4})' pour l'usager '([^']*)' '([^']*)'$") {
				type: String, year: Number, lastname: String, firstname: String ->
			val currentUser = userRepository.findAll().first { it.lastname == lastname && it.firstname == firstname }
			val listLeaveBalancesCommand = ListLeaveBalancesCommand()
			val leaveBalances = listLeaveBalances.handle(listLeaveBalancesCommand)
			assertEquals(
				1,
				leaveBalances.count { leaveBalance ->
					leaveBalance.userId == currentUser.id
							&& leaveBalance.leaveType == LeaveType.valueOf(type)
							&& leaveBalance.balanceType == BalanceType.CREDIT
							&& leaveBalance.year == year.toInt()
				}
			)
		}

		Then("^il existe '([^']*)' types de soldes pour l'année '([^']*)' pour l'usager '([^']*)' '([^']*)'$") {
				expectedNumber: Int, year: Int, lastname: String, firstname: String ->
			val currentUser = userRepository.findAll().first { it.lastname == lastname && it.firstname == firstname }
			val listLeaveBalancesCommand = ListLeaveBalancesCommand(years = setOf(year))
			val leaveBalances = listLeaveBalances.handle(listLeaveBalancesCommand)

			assertEquals(
				expectedNumber,
				leaveBalances.filter { leaveBalance ->
					leaveBalance.userId == currentUser.id && leaveBalance.year == year
				}.distinctBy { it.leaveType }.count()
			)
		}

		And("^le solde total pour le type '([^']*)' pour l'année '([^']*)' pour l'usager '([^']*)' '([^']*)' est égal à '([^']*)'$") {
				type: String, year: Int, lastname: String, firstname: String, totalAmount: BigDecimal ->
			val currentUser = userRepository.findAll().first { it.lastname == lastname && it.firstname == firstname }
			val listLeaveBalancesCommand = ListLeaveBalancesCommand(years = setOf(year))
			val leaveBalances = listLeaveBalances.handle(listLeaveBalancesCommand)

			assertEquals(
				totalAmount,
				leaveBalances.filter { leaveBalance ->
					leaveBalance.userId == currentUser.id && leaveBalance.year == year
				}.sumOf { it.amount.toInt() }.toBigDecimal()
			)

		}

		Then("^les soldes pour cette demande existent selon la répartition annual: \"([^']*)\", recovery: \"([^']*)\", special: \"([^']*)\", WTR: \"([^']*)\", TSA: \"([^']*)\"$") {
				annualBalance: BigDecimal, recoveryBalance: BigDecimal, specialBalance: BigDecimal, WTRBalance: BigDecimal, TSABalance: BigDecimal ->
			TODO("not yet implemented")
		}

	}
}

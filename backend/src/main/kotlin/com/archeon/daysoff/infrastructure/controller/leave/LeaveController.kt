package com.archeon.daysoff.infrastructure.controller.leave

import com.archeon.daysoff.business.useCase.leave.createALeaveRequest.CreateALeaveRequest
import com.archeon.daysoff.business.useCase.leave.createALeaveRequest.CreateALeaveRequestCommand
import com.archeon.daysoff.business.useCase.leave.deleteALeaveRequest.DeleteALeaveRequest
import com.archeon.daysoff.business.useCase.leave.deleteALeaveRequest.DeleteALeaveRequestCommand
import com.archeon.daysoff.business.useCase.leave.listLeaves.ListLeaveRequests
import com.archeon.daysoff.business.useCase.leave.listLeaves.ListLeaveRequestsCommand
import com.archeon.daysoff.business.useCase.leave.refuseALeaveRequest.RefuseALeaveRequest
import com.archeon.daysoff.business.useCase.leave.refuseALeaveRequest.RefuseALeaveRequestCommand
import com.archeon.daysoff.business.useCase.leave.setLeaveRequestAsDraft.SetLeaveRequestAsDraft
import com.archeon.daysoff.business.useCase.leave.setLeaveRequestAsDraft.SetLeaveRequestAsDraftCommand
import com.archeon.daysoff.business.useCase.leave.submitALeaveRequest.SubmitALeaveRequest
import com.archeon.daysoff.business.useCase.leave.submitALeaveRequest.SubmitALeaveRequestCommand
import com.archeon.daysoff.business.useCase.leave.updateALeaveRequest.UpdateALeaveRequest
import com.archeon.daysoff.business.useCase.leave.updateALeaveRequest.UpdateALeaveRequestCommand
import com.archeon.daysoff.business.useCase.leave.validateALeaveRequestByManagement.ValidateALeaveRequestByManagement
import com.archeon.daysoff.business.useCase.leave.validateALeaveRequestByManagement.ValidateALeaveRequestByManagementCommand
import com.archeon.daysoff.business.useCase.leave.validateALeaveRequestByService.ValidateALeaveRequestByService
import com.archeon.daysoff.business.useCase.leave.validateALeaveRequestByService.ValidateALeaveRequestByServiceCommand
import com.archeon.daysoff.infrastructure.converter.output.leave.LeaveOutputConverter
import com.archeon.daysoff.infrastructure.resource.output.leave.LeaveOutput
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("leaveRequest")
class LeaveController(
	private val createALeaveRequest: CreateALeaveRequest,
	private val submitALeaveRequest: SubmitALeaveRequest,
	private val setLeaveRequestAsDraft: SetLeaveRequestAsDraft,
	private val refuseALeaveRequest: RefuseALeaveRequest,
	private val deleteALeaveRequest: DeleteALeaveRequest,
	private val validateALeaveRequestByService: ValidateALeaveRequestByService,
	private val validateALeaveRequestByManagement: ValidateALeaveRequestByManagement,
	private val updateALeaveRequest: UpdateALeaveRequest,
	private val listLeaveRequests: ListLeaveRequests,
	private val leaveOutputConverter: LeaveOutputConverter
) : LeaveApi {

	@GetMapping("listLeaveRequests")
	override fun listLeaveRequests(): List<LeaveOutput> {
		return listLeaveRequests.handle(ListLeaveRequestsCommand()).map { leaveOutputConverter.toResource(it) }.toList()
	}

	@PatchMapping("submitALeaveRequest")
	override fun submitALeaveRequest(submitALeaveRequestCommand: SubmitALeaveRequestCommand): LeaveOutput {
		return leaveOutputConverter.toResource(submitALeaveRequest.handle(submitALeaveRequestCommand))
	}

	@PatchMapping("validateALeaveRequestByService")
	override fun validateALeaveRequestByService(validateALeaveRequestByServiceCommand: ValidateALeaveRequestByServiceCommand): LeaveOutput {
		return leaveOutputConverter.toResource(validateALeaveRequestByService.handle(validateALeaveRequestByServiceCommand))
	}

	@PatchMapping("validateALeaveRequestByManagement")
	override fun validateALeaveRequestByManagement(validateALeaveRequestByManagementCommand: ValidateALeaveRequestByManagementCommand): LeaveOutput {
		return leaveOutputConverter.toResource(validateALeaveRequestByManagement.handle(validateALeaveRequestByManagementCommand))
	}

	@PatchMapping("refuseALeaveRequest")
	override fun refuseALeaveRequest(refuseALeaveRequestCommand: RefuseALeaveRequestCommand): LeaveOutput {
		return leaveOutputConverter.toResource(refuseALeaveRequest.handle(refuseALeaveRequestCommand))
	}

	@PatchMapping("deleteALeaveRequest")
	override fun deleteALeaveRequest(deleteALeaveRequestCommand: DeleteALeaveRequestCommand): LeaveOutput {
		return leaveOutputConverter.toResource(deleteALeaveRequest.handle(deleteALeaveRequestCommand))
	}

	@PatchMapping("updateALeaveRequest")
	override fun updateALeaveRequest(updateALeaveRequestCommand: UpdateALeaveRequestCommand): LeaveOutput {
		return leaveOutputConverter.toResource(updateALeaveRequest.handle(updateALeaveRequestCommand))
	}

	@PatchMapping("setLeaveRequestAsDraft")
	override fun setLeaveRequestAsDraft(setLeaveRequestAsDraftCommand: SetLeaveRequestAsDraftCommand): LeaveOutput {
		return leaveOutputConverter.toResource(setLeaveRequestAsDraft.handle(setLeaveRequestAsDraftCommand))
	}

	@PostMapping
	override fun createALeaveRequest(@RequestBody createALeaveRequestCommand: CreateALeaveRequestCommand): LeaveOutput {
		return leaveOutputConverter.toResource(createALeaveRequest.handle(createALeaveRequestCommand))
	}
}

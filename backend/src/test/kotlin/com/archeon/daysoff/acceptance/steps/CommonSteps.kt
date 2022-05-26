package com.archeon.daysoff.acceptance.steps

import com.archeon.daysoff.acceptance.SharedData
import com.archeon.daysoff.business.util.ErrorUtil.ACCESS_FORBIDDEN
import com.archeon.daysoff.business.util.ErrorUtil.A_VALID_REQUEST_EXISTS_IN_THE_PERIOD
import com.archeon.daysoff.business.util.ErrorUtil.TEAM_ALREADY_EXISTS
import com.archeon.daysoff.business.util.ErrorUtil.TEAM_WITH_USERS
import com.archeon.daysoff.business.util.ErrorUtil.USER_EMAIL_ALREADY_USED
import com.archeon.daysoff.business.util.ErrorUtil.YOU_CAN_ONLY_CREATE_LEAVE_REQUEST_FOR_YOURSELF
import com.archeon.daysoff.business.util.ErrorUtil.YOU_CAN_ONLY_CREATE_LEAVE_REQUEST_IN_YOUR_TEAM
import com.archeon.daysoff.business.util.ErrorUtil.YOU_CAN_ONLY_UPDATE_DRAFT_LEAVE_REQUEST
import com.archeon.daysoff.business.util.ErrorUtil.YOU_CAN_ONLY_UPDATE_LEAVE_IN_YOUR_TEAM
import com.archeon.daysoff.business.util.ErrorUtil.YOU_CAN_ONLY_UPDATE_LEAVE_REQUEST_FOR_YOURSELF
import io.cucumber.java8.En
import io.cucumber.java8.Scenario
import io.cucumber.spring.CucumberTestContext
import org.springframework.context.annotation.Scope
import kotlin.test.assertEquals
import kotlin.test.assertNull

class CommonSteps(
	sharedData: SharedData
) : En {

	init {
		And("aucune erreur n'a été produite") {
			assertNull(sharedData.currentException)
		}

		Then("Une erreur signalant que je n'ai pas les droits est produite") {
			assertEquals(ACCESS_FORBIDDEN, sharedData.currentException?.message)
		}

		Then("Une erreur signalant que cet email est déjà pris est produite") {
			assertEquals(USER_EMAIL_ALREADY_USED, sharedData.currentException?.message)
		}

		Then("Une erreur signalant que nom d'équipe est déjà utilisé est produite") {
			assertEquals(TEAM_ALREADY_EXISTS, sharedData.currentException?.message)
		}

		Then("^Une erreur signalant que cette équipe est attachée a des membres est levée$") {
			assertEquals(TEAM_WITH_USERS, sharedData.currentException?.message)
		}

		Then("^une erreur me signalant qu'une demande est déjà en cours pour cette période est produite$") {
			assertEquals(A_VALID_REQUEST_EXISTS_IN_THE_PERIOD, sharedData.currentException?.message)
		}

		Then("^une erreur me signalant que je ne peux modifier que les demandes à l'état de brouillon est produite$") {
			assertEquals(YOU_CAN_ONLY_UPDATE_DRAFT_LEAVE_REQUEST, sharedData.currentException?.message)
		}

		Then("^une erreur signalant que je ne peux modifier que les demandes de mes équipe est produite$") {
			assertEquals(YOU_CAN_ONLY_UPDATE_LEAVE_IN_YOUR_TEAM, sharedData.currentException?.message)
		}

		Then("^une erreur signalant que je ne peux modifier que mes demandes est produite$") {
			assertEquals(YOU_CAN_ONLY_UPDATE_LEAVE_REQUEST_FOR_YOURSELF, sharedData.currentException?.message)
		}

		Then("^une erreur signalant que je ne peux créer que des demandes dans mes équipes est levée$") {
			assertEquals(YOU_CAN_ONLY_CREATE_LEAVE_REQUEST_IN_YOUR_TEAM, sharedData.currentException?.message)
		}

		Then("^une erreur signalant que je ne peux créer que des demandes pour moi est levée$") {
			assertEquals(YOU_CAN_ONLY_CREATE_LEAVE_REQUEST_FOR_YOURSELF, sharedData.currentException?.message)
		}
	}
}

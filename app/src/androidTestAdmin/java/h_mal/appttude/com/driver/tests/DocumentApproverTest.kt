package h_mal.appttude.com.driver.tests

import androidx.test.espresso.Espresso
import h_mal.appttude.com.driver.R
import h_mal.appttude.com.driver.robots.approver
import h_mal.appttude.com.driver.robots.driverOverview
import h_mal.appttude.com.driver.robots.homeAdmin
import org.junit.Test

class DocumentApproverTest : AdminBaseTest() {

    @Test
    fun loginAsAdmin_approveDocumentForDriver_documentApproved() {
        homeAdmin {
            waitUntilDisplayed()
            clickOnItem("kabirmhkhan@gmail.com")
        }
        // Approve check
        driverOverview {
            waitForListViewToDisplay()
            clickOnItemAtPosition(0)
        }
        approver {
            clickApprove()
            Espresso.pressBack()
        }
        driverOverview {
            waitForListViewToDisplay()
            clickOnItemAtPosition(2)
        }
        approver {
            clickApprove()
            Espresso.pressBack()
        }
        driverOverview {
            waitForListViewToDisplay()
            matchView(2, getResourceString(R.string.approved))
        }
    }

    @Test
    fun loginAsAdmin_declineDocumentForDriver_documentDeclined() {
        homeAdmin {
            waitUntilDisplayed()
            // TODO: find a better way to waitw
            waitFor(1200)
            clickOnItem("kabirmhkhan@gmail.com")
        }

        // Decline check
        driverOverview {
            waitForListViewToDisplay()
            clickOnItemAtPosition(3)
        }
        approver {
            clickDecline()
            Espresso.pressBack()
        }
        driverOverview {
            waitForListViewToDisplay()
            clickOnItemAtPosition(1)
        }
        approver {
            clickDecline()
            Espresso.pressBack()
        }
        driverOverview {
            waitForListViewToDisplay()
            matchView(1, getResourceString(R.string.denied))
        }
    }

    @Test
    fun loginAsAdmin_verifyNoDocumentForNewDriver() {
        homeAdmin {
            waitUntilDisplayed()
            clickOnItem("fanasid@gmail.com")
        }
        driverOverview {
            waitForListViewToDisplay()
            matchView(0, getResourceString(R.string.not_submitted))
            clickOnItemAtPosition(0)
            matchView(0, getResourceString(R.string.not_submitted))
        }
    }

}
package h_mal.appttude.com.driver.tests

import androidx.test.espresso.Espresso
import h_mal.appttude.com.driver.robots.approver
import h_mal.appttude.com.driver.robots.driverOverview
import h_mal.appttude.com.driver.robots.homeAdmin
import h_mal.appttude.com.driver.R
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
            clickOnItemAtPosition(0)
        }
        approver {
            clickApprove()
            checkToastMessage("Document already approved")
            Espresso.pressBack()
        }
        driverOverview {
            clickOnItemAtPosition(2)
        }
        approver {
            clickApprove()
            checkSnackBarDisplayedByMessage("Document has been approved")
            Espresso.pressBack()
        }
        driverOverview {
            matchView(2, getResourceString(R.string.approved))
        }

        // Decline check
        driverOverview {
            clickOnItemAtPosition(3)
        }
        approver {
            clickDecline()
            checkToastMessage("Document already declined")
            Espresso.pressBack()
        }
        driverOverview {
            clickOnItemAtPosition(1)
        }
        approver {
            clickDecline()
            checkSnackBarDisplayedByMessage("Document has been declined")
            Espresso.pressBack()
        }
        driverOverview {
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
            matchView(0, getResourceString(R.string.not_submitted))
            clickOnItemAtPosition(0)
            matchView(0, getResourceString(R.string.not_submitted))
        }
    }

}
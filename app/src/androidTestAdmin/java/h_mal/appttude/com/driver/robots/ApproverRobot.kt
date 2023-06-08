package h_mal.appttude.com.driver.robots

import h_mal.appttude.com.driver.BaseTestRobot
import h_mal.appttude.com.driver.R


fun approver(func: ApproverRobot.() -> Unit) = ApproverRobot().apply { func() }
class ApproverRobot : BaseTestRobot() {

    fun clickApprove() = clickButton(R.id.approve)
    fun clickDecline() = clickButton(R.id.decline)

}
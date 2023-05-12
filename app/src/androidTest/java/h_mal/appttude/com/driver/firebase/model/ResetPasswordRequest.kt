package h_mal.appttude.com.driver.firebase.model

data class ResetPasswordRequest(
	val oldPassword: String? = null,
	val tenantId: String? = null,
	val newPassword: String? = null,
	val oobCode: String? = null,
	val email: String? = null
)


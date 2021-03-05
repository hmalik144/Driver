package h_mal.appttude.com.driver.data

sealed class DataFieldState {
    object DefaultState : DataFieldState()
    object NonUserSateUpdated: DataFieldState()
    object UserUpdateState: DataFieldState()
}
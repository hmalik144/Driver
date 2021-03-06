package h_mal.appttude.com.driver.data

sealed class DataFieldState {
    object DefaultState : DataFieldState()
    object NonUserStateUpdated: DataFieldState()
    object UserUpdateState: DataFieldState()
}
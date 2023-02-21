package h_mal.appttude.com.data

sealed class DataFieldState {
    object DefaultState : DataFieldState()
    object NonUserStateUpdated: DataFieldState()
    object UserUpdateState: DataFieldState()
}
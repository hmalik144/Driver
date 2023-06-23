package h_mal.appttude.com.driver.model

import h_mal.appttude.com.driver.R

enum class DatabaseStatus(val drawable: Int, val header: Int, val subtext: Int) {
    NO_CONNECTION(R.drawable.baseline_inbox_24, R.string.no_connection, R.string.no_connection_subtext),
    NO_PERMISSION(
        R.drawable.baseline_inbox_24,
        R.string.no_permission,
        R.string.no_permission_subtext
    ),
    CANNOT_RETRIEVE(
        R.drawable.baseline_inbox_24,
        R.string.cannot_retrieve,
        R.string.cannot_retrieve_subtext
    ),
    NO_AUTHORIZATION(
        R.drawable.baseline_inbox_24,
        R.string.no_authorization,
        R.string.no_authorization_subtext
    ),
    EMPTY_RESULTS(
        R.drawable.baseline_inbox_24,
        R.string.no_drivers_to_show,
        R.string.no_drivers_subtext
    )
}
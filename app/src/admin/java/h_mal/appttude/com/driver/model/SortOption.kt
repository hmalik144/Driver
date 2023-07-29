package h_mal.appttude.com.driver.model

enum class SortOption(val key: String, val label: String) {
    NAME("driver_profile/driver_details/forenames","Driver Name"),
    NUMBER("driver_number", "Driver Number");
//        APPROVAL("forenames")

    companion object {
        fun getSortOptionByLabel(label: String?): SortOption? {
            return values().firstOrNull { i -> i.label == label }
        }
        fun getPositionByLabel(label: String?): Int? {
            val sortOption = getSortOptionByLabel(label) ?: return null
            return values().indexOf(sortOption)
        }
    }
}
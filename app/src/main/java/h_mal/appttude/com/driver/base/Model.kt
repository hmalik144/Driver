package h_mal.appttude.com.driver.base

interface Model

interface Document : Model
interface ImageDocument : Document {
    fun getImageFileName(): String?
    fun setImageFileName(fileName: String)
}

interface MultiImageDocument : Document {
    fun getImageFileNames(): List<String>?
    fun setImageFileNames(images: List<String>)
}
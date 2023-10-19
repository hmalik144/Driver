package h_mal.appttude.com.driver.data

import h_mal.appttude.com.driver.base.ImageDocument

data class ImageDocumentFile<T : ImageDocument>(
    val document: T,
    val image: ImageResults
)
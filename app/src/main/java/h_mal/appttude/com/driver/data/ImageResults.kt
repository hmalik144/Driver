package h_mal.appttude.com.driver.data

import com.google.firebase.storage.StorageReference

data class ImageResults(
    val image: StorageReference?,
    val thumbnail: StorageReference?
)

data class ImageCollection(
    val collection: List<Pair<StorageReference, StorageReference>>
)

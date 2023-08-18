package h_mal.appttude.com.driver.helpers

import android.content.ClipData
import android.content.ClipData.Item
import android.net.Uri
import java.io.File

object DataHelper {

    fun createClipItem(filePath: String) = Item(
        Uri.fromFile(
            File(filePath)
        )
    )

    fun createClipData(item: Item, mimeType: String = "text/uri-list") =
        ClipData(null, arrayOf(mimeType), item)

    fun createClipData(filePath: String) = createClipData(createClipItem(filePath))

    fun createClipData(filePaths: List<String>): ClipData {
        val clipData = createClipData(filePaths[0])
        filePaths.filterIndexed { i, _ -> i > 0 }.let { clipData.addFilePaths(it.toTypedArray()) }
        return clipData
    }

    fun createClipData(uri: Uri) = createClipData(Item(uri))

    fun ClipData.addFilePaths(filePaths: Array<String>) {
        filePaths.forEach { addItem(createClipItem(it)) }
    }
}
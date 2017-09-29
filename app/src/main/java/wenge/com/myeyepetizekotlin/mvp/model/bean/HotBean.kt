package wenge.com.myeyepetizekotlin.mvp.model.bean

/**
 * Created by WENGE on 2017/9/29.
 * 备注：
 */


data class HotBean(var count: Int, var total: Int, var nextPageUrl: Any?, var itemList: List<ItemListBean>?) {

    data class ItemListBean(var type: String?, var data: DataBean?, var tag: Any?) {

        data class DataBean(
                var author: Any?,
                var category: String?,
                var isCollected: Boolean,
                var consumption: ConsumptionBean?,
                var cover: CoverBean?,
                var dataType: String?,
                var date: Long,
                var description: String?,
                var descriptionEditor: String?,
                var descriptionPgc: Any?,
                var duration: Long,
                var id: Int,
                var idx: Int,
                var labelList: List<*>?,
                var library: String?,
                var playInfo: List<PlayInfoBean>?,
                var playUrl: String?,
                var isPlayed: Boolean,
                var provider: ProviderBean?,
                var releaseTime: Long,
                var slogan: Any?,
                var subtitles: List<*>?,
                var tags: List<TagsBean>?,
                var title: String?,
                var titlePgc: Any?,
                var type: String?,

                var webAdTrack: Any?,
                var thumbPlayUrl: Any?,
                var campaign: Any?,
                var waterMarks: Any?,
                var adTrack: Any?,
                var remark: Any?,
                var shareAdTrack: Any?,
                var promotion: Any?,
                var label: Any?,
                var lastViewTime: Any?,
                var favoriteAdTrack: Any?) {


            data class ProviderBean(var name: String?, var alias: String?, var icon: String?) {
            }

            data class CoverBean(var feed: String?, var detail: String?, var blurred: String?,
                                 var sharing: Any?, var homepage: Any?) {
            }


            data class ConsumptionBean(var collectionCount: Int, var shareCount: Int, var replyCount: Int) {
            }

            data class PlayInfoBean(var height: Int, var width: Int, var name: String?,
                                    var type: String?, var url: String?, var urlList: List<UrlListBean>?) {

                data class UrlListBean(var name: String?, var url: String?, var size: Int) {
                }
            }

            data class TagsBean(var id: Int, var name: String?, var actionUrl: String?, var adTrack: Any?) {
            }
        }
    }
}

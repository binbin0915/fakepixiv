package com.lyj.fakepivix.app.data.model.response

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.squareup.moshi.JsonClass

/**
 * @author greensun
 *
 * @date 2019/3/20
 *
 * @desc
 */
@JsonClass(generateAdapter = true)
data class IllustListResp(
        val contest_exists: Boolean = false,
        val illusts: List<Illust> = listOf(),
        val next_url: String = "",
        val privacy_policy: PrivacyPolicy = PrivacyPolicy(),
        val ranking_illusts: List<Illust> = listOf()
)


@JsonClass(generateAdapter = true)
data class Illust (
        val caption: String = "",
        val create_date: String = "",
        val height: Int = 0,
        val id: Long = 0,
        val image_urls: ImageUrls = ImageUrls(),
        val is_bookmarked: Boolean = false,
        val is_muted: Boolean = false,
        val meta_pages: List<MetaPage> = listOf(),
        val meta_single_page: MetaSinglePage = MetaSinglePage(),
        val page_count: Int = 0,
        val restrict: Int = 0,
        val sanity_level: Int = 0,
        val series: Series?,
        val tags: List<Tag> = listOf(),
        val title: String = "",
        val tools: List<Any> = listOf(),
        val total_bookmarks: Int = 0,
        val total_view: Int = 0,
        val type: String = "",
        val user: User? = null,
        val visible: Boolean = false,
        val width: Int = 0,
        val x_restrict: Int = 0
): MultiItemEntity {
    companion object {
        const val TYPE_ILLUST = 1
        const val TYPE_PIXIVISION = 2
    }

    override fun getItemType(): Int = when(type) {
        "illust" -> TYPE_ILLUST
        else -> TYPE_ILLUST
    }
}

@JsonClass(generateAdapter = true)
data class User(
        val account: String = "",
        val id: String = "",
        val is_mail_authorized: Boolean = false,
        val is_premium: Boolean = false,
        val mail_address: String = "",
        val name: String = "",
        val is_followed: Boolean = false,
        val profile_image_urls: ProfileImageUrls = ProfileImageUrls(),
        val require_policy_agreement: Boolean = false,
        val x_restrict: Int = 0
)

@JsonClass(generateAdapter = true)
data class ProfileImageUrls(
        val px_16x16: String = "",
        val px_170x170: String = "",
        val px_50x50: String = "",
        val medium: String = ""
)

@JsonClass(generateAdapter = true)
data class MetaSinglePage(
        val original_image_url: String = ""
)

@JsonClass(generateAdapter = true)
data class MetaPage(
        val image_urls: ImageUrls = ImageUrls()
)

@JsonClass(generateAdapter = true)
data class Tag(
        val name: String = "",
        val translated_name: Any = Any()
)

@JsonClass(generateAdapter = true)
data class Series(
        val id: Int = 0,
        val title: String = ""
)

@JsonClass(generateAdapter = true)
data class ImageUrls(
        val large: String = "",
        val medium: String = "",
        val square_medium: String = ""
)

@JsonClass(generateAdapter = true)
data class PrivacyPolicy(
        val message: String = "",
        val url: String = "",
        val version: String = ""
)

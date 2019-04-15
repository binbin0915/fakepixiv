package com.lyj.fakepivix.app.data.model.response

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * @author greensun
 *
 * @date 2019/3/20
 *
 * @desc
 */

data class IllustListResp(
        val contest_exists: Boolean = false,
        val illusts: List<Illust> = listOf(),
        val next_url: String = "",
        val privacy_policy: PrivacyPolicy = PrivacyPolicy(),
        val ranking_illusts: List<Illust> = listOf()
)


data class Illust (
        val caption: String = "",
        val create_date: String = "",
        val height: Int = 0,
        val id: Int = 0,
        val image_urls: ImageUrls = ImageUrls(),
        val is_bookmarked: Boolean = false,
        val is_muted: Boolean = false,
        val meta_pages: List<Any> = listOf(),
        val meta_single_page: MetaSinglePage = MetaSinglePage(),
        val page_count: Int = 0,
        val restrict: Int = 0,
        val sanity_level: Int = 0,
        val series: Any = Any(),
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
    }

    override fun getItemType(): Int = when(type) {
        "illust" -> TYPE_ILLUST
        else -> TYPE_ILLUST
    }
}

data class User(
        val account: String = "",
        val id: String = "",
        val is_mail_authorized: Boolean = false,
        val is_premium: Boolean = false,
        val mail_address: String = "",
        val name: String = "",
        val profile_image_urls: ProfileImageUrls = ProfileImageUrls(),
        val require_policy_agreement: Boolean = false,
        val x_restrict: Int = 0
)

data class ProfileImageUrls(
        val px_16x16: String = "",
        val px_170x170: String = "",
        val px_50x50: String = ""
)

data class MetaSinglePage(
        val original_image_url: String = ""
)

data class Tag(
        val name: String = "",
        val translated_name: Any = Any()
)

data class ImageUrls(
        val large: String = "",
        val medium: String = "",
        val square_medium: String = ""
)


data class PrivacyPolicy(
        val message: String = "",
        val url: String = "",
        val version: String = ""
)

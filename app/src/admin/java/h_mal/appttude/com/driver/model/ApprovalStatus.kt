package h_mal.appttude.com.driver.model

import h_mal.appttude.com.driver.R

enum class ApprovalStatus(val stringId: Int, val drawableId: Int, val score: Int) {
    NOT_SUBMITTED(R.string.not_submitted, R.drawable.denied, 0),
    DENIED(R.string.denied, R.drawable.denied, 1),
    PENDING_APPROVAL(R.string.pending, R.drawable.pending, 2),
    APPROVED(R.string.approved, R.drawable.approved, 3);

    companion object {
        infix fun getByScore(value: Int): ApprovalStatus? =
            ApprovalStatus.values().firstOrNull { it.score == value }
        infix fun getByStringId(value: Int): ApprovalStatus? =
            ApprovalStatus.values().firstOrNull { it.stringId == value }
        infix fun getByDrawableId(value: Int): ApprovalStatus? =
            ApprovalStatus.values().firstOrNull { it.drawableId == value }
    }
}
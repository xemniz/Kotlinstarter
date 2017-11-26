package ru.xmn.kotlinstarter.features.movies

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.item_reply.view.*
import kotlinx.android.synthetic.main.layout_comments.*
import ru.xmn.common.adapter.LastAdapter
import ru.xmn.common.adapter.lastAdapterItems
import ru.xmn.kotlinstarter.R
import ru.xmn.kotlinstarter.features.movies.elasticdrag.ElasticDragDismissFrameLayout


class MovieDetailsActivity : AppCompatActivity() {
    lateinit var chromeFader: ElasticDragDismissFrameLayout.SystemChromeFader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        blurBackForPoster(poster, posterBlurBack, .85f)
        listReviews.adapter = LastAdapter()
        listReviews.lastAdapterItems = provideComments()
        buttonHideArrow.setOnClickListener { goBack() }
        chromeFader = ElasticDragDismissFrameLayout.SystemChromeFader(this)

        if (savedInstanceState == null)
            posterStarsLayout.animate().alpha(1f).setStartDelay(500).start()
    }

    override fun onResume() {
        super.onResume()
        draggableFrame.addListener(chromeFader)
    }

    override fun onPause() {
        draggableFrame.removeListener(chromeFader)
        super.onPause()
    }

    override fun onBackPressed() {
        goBack()
    }

    private fun goBack() {
        posterStarsLayout.alpha = 0f
        super.onBackPressed()
    }


    private fun provideComments() = listOf<CommentViewData>(
            CommentViewData(
                    id = "1",
                    owner = "Evgeny Muravjev",
                    text = "Это был просто самый лучший фильм лета. Марго Роби — красавица, Уилл Смит — крут. Сцена с ударом в рожу — просто огонь! ",
                    stars = "2",
                    replies = "Reply",
                    likes = "156",
                    isUserLikeIt = true
            ),
            CommentViewData(
                    id = "2",
                    owner = "Maria",
                    text = "Надо пересмотреть будет еще раз.",
                    stars = "3",
                    replies = "4 Replies",
                    likes = "952",
                    isUserLikeIt = false
            )
    )
}

class CommentViewData(
        val id: String,
        val owner: String,
        val text: String,
        val stars: String,
        val replies: String,
        val likes: String,
        val isUserLikeIt: Boolean
) : LastAdapter.Item() {

    override fun bindOn(view: View) {
        view.apply {
            replierName.text = owner
            commentText.text = text
            starCount.text = stars
            textReply.text = replies
            likesCount.text = likes
            val likeImage = if (isUserLikeIt) R.drawable.ic_favorite_black_24dp else R.drawable.ic_favorite_border_black_24dp
            imageLike.setImageResource(likeImage)
        }
    }

    override fun layoutId() = R.layout.item_reply

    override fun compare(anotherItemValue: Any) =
            (anotherItemValue is CommentViewData) && anotherItemValue.id == id
}
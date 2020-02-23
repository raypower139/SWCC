package ie.swcc.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ie.swcc.R


class NewsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.title = getString(R.string.news_title)
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            NewsFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}

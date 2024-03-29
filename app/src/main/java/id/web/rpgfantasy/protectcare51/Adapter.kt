package id.web.rpgfantasy.protectcare51

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class Adapter(val activity: AppCompatActivity, val fragments: ArrayList<Fragment>) : FragmentStateAdapter(activity) {
    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]
}
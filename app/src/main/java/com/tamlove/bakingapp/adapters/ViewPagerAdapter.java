package com.tamlove.bakingapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tamlove.bakingapp.models.Recipe;
import com.tamlove.bakingapp.ui.RecipeIngredientsListFragment;
import com.tamlove.bakingapp.ui.RecipeStepsListFragment;

import java.util.ArrayList;
import java.util.List;


/*
 *  ViewPager implemented with guidance from the following tutorial -
 *  http://www.gadgetsaint.com/android/create-viewpager-tabs-android/#.W1mdES2ZMWo
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragList = new ArrayList<>();
    private final List<String> mFragTitles = new ArrayList<>();
    private Recipe mRecipes;

    public ViewPagerAdapter(FragmentManager fm, Recipe recipes) {
        super(fm);
        mRecipes = recipes;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return RecipeIngredientsListFragment.newInstance(mRecipes);
            case 1:
                return RecipeStepsListFragment.newInstance(mRecipes);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mFragList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragTitles.get(position);
    }

    public void addFrag(Fragment frag, String title){
        mFragList.add(frag);
        mFragTitles.add(title);
    }
}

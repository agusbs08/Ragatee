package com.hackaton.bri.ragatee.ui.detail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.hackaton.bri.ragatee.ui.detail.fragmentdetail.DetailFragment;
import com.hackaton.bri.ragatee.ui.detail.fragmentmap.MapFragment;
import com.hackaton.bri.ragatee.ui.detail.fragmentpdfviewer.PDFViewerFragment;

public class DetailViewPagerAdapter extends FragmentStatePagerAdapter {

    public DetailViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    private DetailFragment detailFragment;
    private MapFragment mapFragment;

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if(detailFragment == null) {
                    return new DetailFragment();
                }
                return detailFragment;
            case 1:
                if(mapFragment == null) {
                    return new MapFragment();
                }
                return mapFragment;
            case 2:
                return new PDFViewerFragment();
            default:
                if(detailFragment == null) {
                    return new DetailFragment();
                }
                return new DetailFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0: return "DETAILS";
            case 1: return "MAP";
            case 2: return "PROPOSAL";
            default: return "DETAIL";
        }
    }

    public void setDetailFragment(DetailFragment detailFragment) {
        this.detailFragment = detailFragment;
    }

    public void setMapFragment(MapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }
}

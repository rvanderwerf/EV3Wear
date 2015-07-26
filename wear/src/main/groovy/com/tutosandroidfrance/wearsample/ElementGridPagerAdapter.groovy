package com.tutosandroidfrance.wearsample

import android.app.Fragment
import android.app.FragmentManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.wearable.view.CardFragment
import android.support.wearable.view.FragmentGridPagerAdapter
import com.github.florent37.davinci.DaVinci
import groovy.transform.CompileStatic

@CompileStatic
public class ElementGridPagerAdapter extends FragmentGridPagerAdapter {

    private Context context
    private List<Map> androidVersions
    private List<Row> mRows

    public ElementGridPagerAdapter(Context context, List<Map> androidVersions, FragmentManager fm) {
        super(fm)

        this.context = context
        this.androidVersions = androidVersions
        this.mRows = new ArrayList<Row>()

        //Built the table to display elements
        for (Map version : androidVersions) {
            mRows.add(new Row(
                            //we will now put an item by line
                            CardFragment.create(Integer.toString((int)version.get("id")), (String)version.get("joke"))
                    )
            )
        }
    }

    //The fragment to be displayed
    @Override
    public Fragment getFragment(int row, int col) {
        Row adapterRow = mRows.get(row)
        return adapterRow.getColumn(col)
    }

    //the drawable background displayed for the line [row ]
    @Override
    public Drawable getBackgroundForRow(final int row) {
        return DaVinci.with(context).load("http://flashgamedistribution.com/thumbs/flying_chuck_norris_head.png").into(this, row)
    }

    @Override
    public Drawable getBackgroundForPage(final int row, final int column) {
        //we can specify a different drawable for horizontal swipe
        return getBackgroundForRow(row)
    }

    //The number of rows in the grid
    @Override
    public int getRowCount() {
        return mRows.size()
    }

    //The number of columns per row
    @Override
    public int getColumnCount(int rowNum) {
        return mRows.get(rowNum).getColumnCount()
    }

    /**
     * Representation of a line - Contains a list of fragments
     */
    private class Row {
        final List<Fragment> columns = new ArrayList<Fragment>()

        public Row(Fragment... fragments) {
            for (Fragment f : fragments) {
                add(f)
            }
        }

        public void add(Fragment f) {
            columns.add(f)
        }

        Fragment getColumn(int i) {
            return columns.get(i)
        }

        public int getColumnCount() {
            return columns.size()
        }
    }

}
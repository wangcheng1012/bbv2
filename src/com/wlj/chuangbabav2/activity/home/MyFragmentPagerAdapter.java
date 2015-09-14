package com.wlj.chuangbabav2.activity.home;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

/**
 * 
 * @author wlj
 *
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

	public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> list) {
		super(fm);
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}
	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
	 * 
	 * <li>生成新的 Fragment 对象。重载该函数时需要注意这一点。在需要时，该函数将被 instantiateItem() 所调用</li>
	 * <li>如果需要向 Fragment 对象传递相对静态的数据时，我们一般通过 Fragment.setArguments() 来进行，
	 *		这部分代码应当放到 getItem()。它们只会在新生成 Fragment 对象时执行一遍</li>
	 * <li>如果需要在生成 Fragment 对象后，将数据集里面一些动态的数据传递给该 Fragment，那么，
	 * 		这部分代码不适合放到 getItem() 中。因为当数据集发生变化时，往往对应的 Fragment 已经生成，
	 * 		如果传递数据部分代码放到了 getItem() 中，这部分代码将不会被调用。这也是为什么很多人发现调用 
	 * 		PagerAdapter.notifyDataSetChanged() 后，getItem() 没有被调用的一个原因</li>
	 */
	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * 会对 Fragment 进行 FragmentTransaction.detach()
	 */
	@Override
	public void destroyItem(ViewGroup container, int position,Object object) {
		// TODO Auto-generated method stub
		super.destroyItem(container, position, object);
	}
	/*
	 * <li> 函数中判断一下要生成的 Fragment 是否已经生成过了，如果生成过了，就使用旧的，
	 * 		旧的将被 Fragment.attach()；如果没有，就调用 getItem() 生成一个新的，新的
	 * 		对象将被 FragmentTransation.add()。</li>
	 * <li>FragmentPagerAdapter 会将所有生成的 Fragment 对象通过 FragmentManager 
	 * 		保存起来备用，以后需要该 Fragment 时，都会从 FragmentManager 读取，而不会再次调用 getItem() 方法</li>
	 * <li>如果需要在生成 Fragment 对象后，将数据集中的一些数据传递给该 Fragment，这部分代码应该放到这个函数的重载里。
	 * 		在我们继承的子类中，重载该函数，并调用 FragmentPagerAdapter.instantiateItem() 取得该函数返回 Fragment 对象，
	 * 		然后，我们该 Fragment 对象中对应的方法，将数据传递过去，然后返回该对象</li>
	 * <li>否则，如果将这部分传递数据的代码放到 getItem()中，在 PagerAdapter.notifyDataSetChanged() 后，
	 * 		这部分数据设置代码将不会被调用</li>
	 */
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		// TODO Auto-generated method stub
		return super.instantiateItem(container, position);
	}
	@Override
	public int getItemPosition(Object object) {
//		super.getItemPosition(object); 默认为 POSITION_UNCHANGED
//		PagerAdapter.POSITION_UNCHANGED   什么都不做
//		PagerAdapter.POSITION_NONE 则调用 PagerAdapter.destroyItem() 来去掉该对象，
//		并设置为需要刷新 (needPopulate = true) 以便触发PagerAdapter.instantiateItem() 来生成新的对象
		return PagerAdapter.POSITION_NONE;
	}
	
}

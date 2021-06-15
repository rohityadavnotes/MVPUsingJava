package com.mvp.using.java.ui.dashboard.fragmentnavigator;

import android.os.Bundle;

import com.mvp.using.java.ui.dashboard.fragment.aboutus.AboutUsFragment;
import com.mvp.using.java.ui.dashboard.fragment.contactus.ContactUsFragment;
import com.mvp.using.java.ui.dashboard.fragment.ourbankaccount.OurBankAccountsFragment;
import com.mvp.using.java.ui.dashboard.fragment.product.ProductFragment;
import com.mvp.using.java.ui.dashboard.fragment.productcategory.ProductCategoryFragment;
import com.mvp.using.java.ui.dashboard.fragment.productdetail.ProductDetailFragment;
import com.mvp.using.java.ui.dashboard.fragment.profile.ProfileFragment;
import com.mvp.using.java.ui.dashboard.fragment.shoppingcart.ShoppingCartFragment;

public class FragmentFactory {

    public static MyAppFragment getMyAppFragment(AppSection section, Bundle bundle) {
        switch (section) {
            case PRODUCT_CATEGORY:
                return ProductCategoryFragment.newInstance(bundle);
            case PRODUCT:
                return ProductFragment.newInstance(bundle);
            case PRODUCT_DETAIL:
                return ProductDetailFragment.newInstance(bundle);
            case PROFILE:
                return ProfileFragment.newInstance(bundle);
            case SHOPPING_CART:
                return ShoppingCartFragment.newInstance(bundle);
            case OUR_BANK_ACCOUNT:
                return OurBankAccountsFragment.newInstance(bundle);
            case ABOUT_US:
                return AboutUsFragment.newInstance(bundle);
            case CONTACT_US:
                return ContactUsFragment.newInstance(bundle);
            default:
                return null;
        }
    }
}
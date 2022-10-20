package com.Doctoor.app.utils.font;

import com.Doctoor.app.R;

public enum TypefacesFont implements TypefaceUtil.TypefaceId {
    MARK_PRO_RAGULAR(R.font.mark_pro_ragular),
    MARK_PRO_LIGHT(R.font.mark_pro_light),
    MARK_PRO_MEDIUM(R.font.mark_pro_medium),
    MARK_PRO_BOLD(R.font.mark_pro_bold),
    MARK_PRO_BOOK(R.font.mark_pro_book);

    private int font;

    TypefacesFont(int filename) {
        this.font = filename;
    }

    public static TypefacesFont from(int index) {
        switch (index) {
            case 0:
                return MARK_PRO_RAGULAR;
            case 1:
                return MARK_PRO_LIGHT;
            case 2:
                return MARK_PRO_MEDIUM;
            case 3:
                return MARK_PRO_BOLD;

            case 4:
                return MARK_PRO_BOOK;

            default:
                return MARK_PRO_RAGULAR;
        }
    }

    @Override
    public android.graphics.Typeface get() {
        return TypefaceUtil.getTypeface(this);
    }

    @Override
    public int getFont() {
        return font;
    }
}
package scut.carson_ho.searchview.adapter;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import scut.carson_ho.searchview.DrawableCenterTextView;
import scut.carson_ho.searchview.R;

/**
 * =======================================================
 * Author: liying - liruoer2008@yeah.net
 * Datetime: 2018/9/1 9:59
 * Description: 历史纪录列表适配器
 * Remarks:
 * =======================================================
 */
public class HistoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<String> mHistories;
    private OnHistoryActionListener mListener;

    // 清除搜索历史设置
    private String mClearHistoryText;
    private ColorStateList mClearHistoryTextColor;
    private float mClearHistoryTextSize;
    @DrawableRes
    private int mClearHistoryTextBackground;
    private boolean mClearHistoryVisible = false;

    public HistoryAdapter(List<String> histories) {
        this.mHistories = histories;
    }

    public void setOnHistoryActionListener(OnHistoryActionListener listener) {
        this.mListener = listener;
    }

    public void setClearHistoryText(String text) {
        this.mClearHistoryText = text;
    }

    public void setClearHistoryTextColor(ColorStateList color) {
        this.mClearHistoryTextColor = color;
    }

    public void setClearHistoryTextSize(float size) {
        this.mClearHistoryTextSize = size;
    }

    public void setClearHistoryTextBackground(@DrawableRes int background) {
        this.mClearHistoryTextBackground = background;
    }

    public void hideClearHistoryText() {
        mClearHistoryVisible = false;
        notifyItemChanged(mHistories.size());
    }

    public void showClearHistoryText() {
        mClearHistoryVisible = true;
        notifyItemChanged(mHistories.size());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false));
        } else {
            return new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clear_histories, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_ITEM) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            final String history = mHistories.get(position);
            itemViewHolder.mTvHistory.setText(history);
            itemViewHolder.mLlItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemClick(holder.getAdapterPosition(), history);
                    }
                }
            });
            itemViewHolder.mIbDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onDelete(holder.getAdapterPosition(), history);
                    }
                }
            });
        } else {
            FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.mTvClear.setVisibility(mClearHistoryVisible ? View.VISIBLE : View.INVISIBLE);
            footerViewHolder.mTvClear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClear();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mHistories.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mHistories.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout mLlItem;
        private TextView mTvHistory;
        private ImageButton mIbDelete;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mLlItem = itemView.findViewById(R.id.ll_item);
            mTvHistory = itemView.findViewById(R.id.tv_history);
            mIbDelete = itemView.findViewById(R.id.ib_delete);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        private DrawableCenterTextView mTvClear;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvClear = itemView.findViewById(R.id.tv_clear);
            mTvClear.setText(mClearHistoryText);
            mTvClear.setTextSize(TypedValue.COMPLEX_UNIT_PX, mClearHistoryTextSize);
            mTvClear.setTextColor(mClearHistoryTextColor);
            // 给 drawable 染色
            Drawable drawableLeft = mTvClear.getCompoundDrawables()[0];
            drawableLeft.setColorFilter(mClearHistoryTextColor.getDefaultColor(), PorterDuff.Mode.SRC_IN);
            mTvClear.setCompoundDrawables(drawableLeft, null, null, null);

            mTvClear.setBackgroundResource(mClearHistoryTextBackground);
        }
    }

    /**
     * 历史记录操作监听器
     */
    public interface OnHistoryActionListener {
        /**
         * 点击条目
         *
         * @param position 位置
         * @param item     条目
         */
        void onItemClick(int position, String item);

        /**
         * 删除条目
         *
         * @param position 位置
         * @param item     条目
         */
        void onDelete(int position, String item);

        /**
         * 清除所有条目
         */
        void onClear();
    }
}

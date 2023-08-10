package fragement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.test.R;

public class Fragment3 extends Fragment {
    private ImageView mIv_1,mIv_2;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        ImageView mIv_2 = view.findViewById(R.id.iv_2);

        Glide.with(this)
                .load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fci.xiaohongshu.com%2F40a4d378-bd37-fdb6-82c3-cf418fce7a26%3FimageView2%2F2%2Fw%2F1080%2Fformat%2Fjpg&refer=http%3A%2F%2Fci.xiaohongshu.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1686277474&t=f7adf4fba4c55f4935ffbdd3a15df491")
                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                .into(mIv_2);
        return view;
    }
}

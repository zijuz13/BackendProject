package com.huajun123.biz;

        import org.springframework.web.multipart.MultipartFile;

        import java.util.List;

public interface IImageBiz {
    List<String> getRollingImages(Integer status);
    void deleteImage(Integer id);
    void uploadImage(MultipartFile file);
}

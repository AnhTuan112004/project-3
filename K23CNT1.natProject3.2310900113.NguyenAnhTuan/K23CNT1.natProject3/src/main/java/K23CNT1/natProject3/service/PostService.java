package K23CNT1.natProject3.service;

import K23CNT1.natProject3.entity.Post;
import K23CNT1.natProject3.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PostService {
    @Autowired private PostRepository postRepo;

    public List<Post> getAllPosts() {
        return postRepo.findAllByOrderByCreatedAtDesc();
    }

    // MỚI: Tìm kiếm bài viết theo từ khóa
    public List<Post> searchPosts(String keyword) {
        if (keyword != null && !keyword.trim().isEmpty()) {
            return postRepo.findByTitleContainingIgnoreCaseOrderByCreatedAtDesc(keyword);
        }
        return getAllPosts();
    }

    public Post getPostById(Long id) {
        return postRepo.findById(id).orElse(null);
    }

    public void savePost(Post post) {
        // Có thể bổ sung set ngày tạo tại đây nếu entity chưa xử lý
        postRepo.save(post);
    }

    public void deletePost(Long id) {
        postRepo.deleteById(id);
    }
    // Import thêm


    // Thêm 2 hàm này
    public Page<Post> getAllPosts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return postRepo.findAll(pageable);
    }

    public Page<Post> searchPosts(String keyword, int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return postRepo.findByTitleContainingIgnoreCase(keyword, pageable);
    }
}
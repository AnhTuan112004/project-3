package K23CNT1NatLap07.K23CNT1.NatLap07.Controller;

import K23CNT1NatLap07.K23CNT1.NatLap07.Service.ProductService;
import K23CNT1NatLap07.K23CNT1.NatLap07.entity.Product;
import K23CNT1NatLap07.K23CNT1.NatLap07.repositoty.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    // Hiển thị danh sách Product
    @GetMapping
    public String listProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        // SỬA LỖI ĐƯỜNG DẪN VIEW
        return "product/product-list";
    }

    // Hiển thị form tạo Product mới
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        Product product = new Product();
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());

        // SỬA LỖI ĐƯỜNG DẪN VIEW
        return "product/product-form";
    }

    // Lưu Product mới hoặc cập nhật
    @PostMapping("/save")
    public String saveProduct(@ModelAttribute("product") Product product) {
        productService.saveProduct(product);
        return "redirect:/products";
    }

    // Hiển thị form chỉnh sửa Product
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (optionalProduct.isPresent()) {
            model.addAttribute("product", optionalProduct.get());
            model.addAttribute("categories", categoryRepository.findAll());
            // SỬA LỖI ĐƯỜNG DẪN VIEW
            return "product/product-form";
        } else {
            return "redirect:/products";
        }
    }

    // Xóa Product theo id
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
package com.slaand.site.service.admin;

import com.slaand.site.exception.ResourceNotFoundException;
import com.slaand.site.mapper.ItemMapper;
import com.slaand.site.model.dto.ItemDto;
import com.slaand.site.model.entity.CategoryEntity;
import com.slaand.site.model.entity.ImageItemEntity;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.repository.CategoryRepository;
import com.slaand.site.repository.ItemRepository;
import com.slaand.site.util.BootstrapUtils;
import com.slaand.site.util.OtherUtils;
import com.slaand.site.util.bootstrap.Alert;
import io.vavr.control.Try;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ItemAdminService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public String executeItems(final Long id, final Model model) {

        final List<ItemEntity> items = retrieveLastTwelveItemList();
        model.addAttribute("items", Objects.requireNonNullElse(items, Collections.emptyList()));
        final List<CategoryEntity> categories = categoryRepository.findAll();
        model.addAttribute("categories", Objects.requireNonNullElse(categories, Collections.emptyList()));
        if (ObjectUtils.isEmpty(id)) {
            model.addAttribute("itemDto", new ItemDto());
        } else {
            ItemEntity item = retrieveSelectedItem(id);
            ItemDto dto = ItemMapper.INSTANCE.itemEntityToItemDto(item);
            model.addAttribute("itemDto", dto);
        }
        return "/admin/items";
    }

    public String createItem(final ItemDto toEdit, final MultipartFile file, final Model model) {

        ItemEntity itemEntity = ItemMapper.INSTANCE.itemDtoToItemEntity(toEdit, categoryRepository);
        itemEntity.setImage(Collections.singletonList(getBase64FromImageFile(file, itemEntity)));
        itemRepository.save(itemEntity);
        BootstrapUtils.setAlertModel(model, Alert.SUCCESS, "Категория создана успешно!");
        return "redirect:/admin/items";
    }

    public String deleteItem(final Long id, final Model model) {

        ItemEntity itemEntity = Try.of(() -> retrieveSelectedItem(id))
                .getOrElse(() -> {
                    BootstrapUtils.setAlertModel(model, Alert.DANGER, "Раздел не был найден!");
                    return null;
                });

        if (ObjectUtils.isEmpty(itemEntity)) {
            return "redirect:/admin/items";
        }

        itemRepository.delete(itemEntity);
        BootstrapUtils.setAlertModel(model, Alert.SUCCESS, "Раздел был успешно удалён!");
        return "redirect:/admin/items";
    }

    public String updateItem(final ItemDto toEdit, final MultipartFile file, final Model model) {

        ItemEntity itemEntity = Try.of(() -> retrieveSelectedItem(toEdit.getId()))
                .getOrElse(() -> {
                    BootstrapUtils.setAlertModel(model, Alert.DANGER, "Раздел не был найден!");
                    return null;
                });
        if(ObjectUtils.isEmpty(itemEntity)) {
            BootstrapUtils.setAlertModel(model, Alert.DANGER, "Данные заказа не найдены!");
            return "redirect:/admin/items";
        }

        ItemMapper.INSTANCE.itemDtoIntoEntity(toEdit, itemEntity, categoryRepository);
        if(!file.isEmpty()) {
            itemEntity.setImage(Collections.singletonList(getBase64FromImageFile(file, itemEntity)));
        }

        itemRepository.save(itemEntity);
        BootstrapUtils.setAlertModel(model, Alert.SUCCESS, "Данные заказа обновлены успешно!");
        return "redirect:/admin/items";
    }

    @SneakyThrows
    private ImageItemEntity getBase64FromImageFile(final MultipartFile file,
                                                   final ItemEntity itemEntity) {
        return ImageItemEntity.builder()
                .itemId(itemEntity)
                .base64(OtherUtils.getBase64FromImageFile(file))
                .build();
    }

    private ItemEntity retrieveSelectedItem(final Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(HttpStatus.NOT_FOUND, "Not found!"));
    }

    private List<ItemEntity> retrieveLastTwelveItemList() {
        return itemRepository.findTop12ByOrderByIdDesc();
    }
    
}
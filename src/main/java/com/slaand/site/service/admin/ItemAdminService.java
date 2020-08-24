package com.slaand.site.service.admin;

import com.slaand.site.mapper.ItemMapper;
import com.slaand.site.model.dto.ItemDto;
import com.slaand.site.model.entity.ItemEntity;
import com.slaand.site.repository.ItemRepository;
import com.slaand.site.util.BootstrapUtils;
import com.slaand.site.util.bootstrap.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
public class ItemAdminService {

    @Autowired
    private ItemRepository itemRepository;

    public Optional<ItemEntity> retrieveSelectedItem(final Long id) {
        return itemRepository.findById(id);
    }

    public List<ItemEntity> retrieveLastTenItemList() {
        return itemRepository.findTop6ByOrderByIdDesc();
    }

    public boolean createItem(final ItemDto toCreate, final Model model) {
        ItemEntity itemEntity = ItemMapper.INSTANCE.itemDtoToItemEntity(toCreate);
        itemRepository.save(itemEntity);
        BootstrapUtils.setAlertModel(model, Alert.SUCCESS, "Категория создана успешно!");
        return true;
    }

    public boolean deleteItem(final Long id, final Model model) {
        Optional<ItemEntity> itemEntity = retrieveSelectedItem(id);
        if (itemEntity.isPresent()) {
            itemRepository.delete(itemEntity.get());
            BootstrapUtils.setAlertModel(model, Alert.SUCCESS, "Раздел был успешно удалён!");
        } else {
            BootstrapUtils.setAlertModel(model, Alert.DANGER, "Раздел не был найден!");
            return false;
        }
        return true;
    }

    public boolean updateItem(final ItemDto itemDto, final Long id, final Model model) {
        ItemEntity itemEntity = retrieveSelectedItem(id).orElse(null);
        if(itemDto == null || itemEntity == null) {
            BootstrapUtils.setAlertModel(model, Alert.DANGER, "Данные заказа не найдены!");
            return false;
        }
        //itemEntity.setItemId(toEdit.getItemId()); todo mapper?
        BootstrapUtils.setAlertModel(model, Alert.SUCCESS, "Данные заказа обновлены успешно!");
        return true;
    }
}
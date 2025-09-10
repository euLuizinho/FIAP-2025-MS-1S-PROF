package com.example.demo.service;

import com.example.demo.model.Voucher;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {
  private final Map<Long, Voucher> vouchers = new ConcurrentHashMap<>();
  private final AtomicLong idGenerator = new AtomicLong();

  public List<Voucher> findAll() {
    return new ArrayList<>(vouchers.values());
  }

  public Voucher findById(Long id) {
    return vouchers.get(id);
  }

  public Voucher findByCode(String code) {
    return vouchers.values().stream()
        .filter(v -> v.getCode().equalsIgnoreCase(code))
        .findFirst()
        .orElse(null);
  }

  public Voucher create(Voucher voucher) {
    voucher.setId(idGenerator.incrementAndGet());
    vouchers.put(voucher.getId(), voucher);
    return voucher;
  }

  public Voucher update(Long id, Voucher voucher) {
    if (!vouchers.containsKey(id)) {
      return null;
    }
    voucher.setId(id);
    vouchers.put(id, voucher);
    return voucher;
  }

  public Voucher patch(Long id, Voucher updates) {
    Voucher existing = vouchers.get(id);
    if (existing == null) {
      return null;
    }

    if (updates.getCode() != null) {
      existing.setCode(updates.getCode());
    }
    if (updates.getDiscountPercent() != null) {
      existing.setDiscountPercent(updates.getDiscountPercent());
    }
    if (updates.getExpirationDate() != null) {
      existing.setExpirationDate(updates.getExpirationDate());
    }
    if (updates.getType() != null) {
      existing.setType(updates.getType());
    }
    if (updates.getMinimumOrderValue() != null) {
      existing.setMinimumOrderValue(updates.getMinimumOrderValue());
    }

    return existing;
  }

  public boolean delete(Long id) {
    return vouchers.remove(id) != null;
  }
}





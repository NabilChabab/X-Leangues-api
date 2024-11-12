package com.example.x_leagues.repository.impl;


import com.example.x_leagues.model.AppUser;
import com.example.x_leagues.repository.AppUserSearchRepository;
import com.example.x_leagues.services.dto.SearchDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AppUserSearchRepositoryImpl implements AppUserSearchRepository {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<SearchDTO> findByCriteria(SearchDTO searchDTO) {
        CriteriaBuilder cb  = entityManager.getCriteriaBuilder();
        CriteriaQuery<SearchDTO> query = cb.createQuery(SearchDTO.class);
        Root<AppUser> user = query.from(AppUser.class);


        query.select(cb.construct(SearchDTO.class,
                user.get("cin"),
                user.get("firstName"),
                user.get("lastName")
        ));

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(searchDTO.getCin())) {
            predicates.add(cb.equal(user.get("cin"), searchDTO.getCin()));
        }
        if (StringUtils.hasText(searchDTO.getFirstName())) {
            predicates.add(cb.like(cb.lower(user.get("firstName")),
                    "%" + searchDTO.getFirstName().toLowerCase() + "%"));
        }
        if (StringUtils.hasText(searchDTO.getLastName())) {
            predicates.add(cb.like(cb.lower(user.get("lastName")),
                    "%" + searchDTO.getLastName().toLowerCase() + "%"));
        }
        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        return entityManager.createQuery(query).getResultList();
    }
}

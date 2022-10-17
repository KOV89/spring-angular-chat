package ru.klimov.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.klimov.backend.model.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {}

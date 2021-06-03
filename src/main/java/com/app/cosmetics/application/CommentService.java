package com.app.cosmetics.application;

import com.app.cosmetics.api.CommentApi;
import com.app.cosmetics.api.exception.NotFoundException;
import com.app.cosmetics.application.data.CommentData;
import com.app.cosmetics.core.comment.Comment;
import com.app.cosmetics.core.comment.CommentRepository;
import com.app.cosmetics.core.item.Item;
import com.app.cosmetics.core.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ItemRepository itemRepository;

    public CommentData create(CommentApi.CommentRequest request) {
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(NotFoundException::new);

        Comment comment = new Comment();
        comment.setItem(item);
        comment.setContent(request.getContent());

        commentRepository.save(comment);

        return toResponse(comment);
    }

    public CommentData findById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        return toResponse(comment);
    }

    @Transactional
    public List<CommentData> findAllByItemId(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(NotFoundException::new)
                .getComments()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CommentData update(Long id, CommentApi.CommentRequest request) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(NotFoundException::new);

        comment.setContent(request.getContent());

        commentRepository.save(comment);

        return toResponse(comment);
    }

    public void delete(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new NotFoundException();
        }
    }

    private CommentData toResponse(Comment comment) {
        CommentData data = new CommentData();

        data.setId(comment.getId());
        data.setContent(comment.getContent());
        data.setItemId(comment.getItem().getId());

        return data;
    }
}

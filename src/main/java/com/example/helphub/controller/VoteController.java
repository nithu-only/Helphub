package com.example.helphub.controller;

import com.example.helphub.dto.VoteDTO;
import com.example.helphub.service.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/votes")
public class VoteController {

    @Autowired
    private VoteService voteService;

    @GetMapping
    public List<VoteDTO> getAllVotes() {
        return voteService.findAll();
    }

    @GetMapping("/{id}")
    public VoteDTO getVoteById(@PathVariable Long id) {
        return voteService.findById(id);
    }

    @PostMapping
    public VoteDTO createVote(@Valid @RequestBody VoteDTO voteDTO) {
        return voteService.save(voteDTO);
    }

    @PutMapping("/{id}")
    public VoteDTO updateVote(@PathVariable Long id, @Valid @RequestBody VoteDTO voteDTO) {
        return voteService.update(id, voteDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteVote(@PathVariable Long id) {
        voteService.delete(id);
    }
}

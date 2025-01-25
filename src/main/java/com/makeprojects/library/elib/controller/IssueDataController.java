package com.makeprojects.library.elib.controller;

import com.makeprojects.library.elib.core.service.definition.IssueDataService;
import com.makeprojects.library.elib.dto.IssueDataDto;
import com.makeprojects.library.elib.entity.IssueData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/issue_data")
public class IssueDataController {

    private IssueDataService issueDataService;

    @Autowired
    public IssueDataController(IssueDataService issueDataService) {
        this.issueDataService = issueDataService;
    }

    @PostMapping("/add")
    public ResponseEntity<IssueData> addIssueData(@RequestBody IssueDataDto issueDataDto) {
        IssueData addedIssueData = this.issueDataService.addIssueData(issueDataDto);
        return new ResponseEntity<>(addedIssueData, HttpStatus.CREATED);
    }

    @PostMapping("/update_status")
    public ResponseEntity<List<IssueData>> markExpStatusByMemberId(@RequestParam UUID memberId) {
        List<IssueData> expiredIssueData = this.issueDataService.markExpStatusOfIssueDataByMemberId(memberId);
        return new ResponseEntity<>(expiredIssueData, HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<List<IssueData>> getAllIssueData() {
        List<IssueData> issueDataList = this.issueDataService.getAll();
        return new ResponseEntity<>(issueDataList, HttpStatus.OK);
    }

    @GetMapping("/{issueDataId}")
    public ResponseEntity<IssueData> getIssueDataById(@PathVariable UUID issueDataId) {
        IssueData issueData = this.issueDataService.get(issueDataId);
        return new ResponseEntity<>(issueData, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<IssueData>> getIssueDataByMemberId(@RequestParam UUID memberId) {
        List<IssueData> issueDataList = this.issueDataService.getIssueDataByMemberId(memberId);
        return new ResponseEntity<>(issueDataList, HttpStatus.OK);
    }
}

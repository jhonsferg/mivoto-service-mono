package pe.com.mivoto.service.datastructures.implementations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pe.com.mivoto.service.datastructures.linear.linkedlist.CustomLinkedList;
import pe.com.mivoto.service.domain.model.VoteRecord;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory record list for tracking vote events and history.
 * Uses {@link CustomLinkedList} for efficient additions and serial lookups.
 */
@Slf4j
@Component
public class VoteRecordList {
    private final CustomLinkedList<VoteRecord> records;

    /**
     * Initializes a new vote record list.
     */
    public VoteRecordList() {
        this.records = new CustomLinkedList<>();
    }

    /**
     * Adds an existing vote record to the list.
     *
     * @param record The VoteRecord object.
     */
    public void addRecord(VoteRecord record) {
        this.records.add(record);
        log.debug("Registro de voto agregado - Total: {}", this.records.size());
    }

    /**
     * Creates and adds a new vote record to the in-memory list.
     *
     * @param voteId     The original vote ID.
     * @param userId     The ID of the user who voted.
     * @param electionId The election ID.
     * @param voteHash   The unique hash of the vote.
     * @return The created VoteRecord.
     */
    public VoteRecord createAndAddRecord(Long voteId, Long userId, Long electionId, String voteHash) {
        VoteRecord record = VoteRecord.builder()
                .voteId(voteId)
                .userId(userId)
                .electionId(electionId)
                .voteHash(voteHash)
                .timestamp(LocalDateTime.now())
                .verified(false)
                .build();

        addRecord(record);
        return record;
    }

    /**
     * Retrieves a record by its index.
     *
     * @param index The index in the list.
     * @return The VoteRecord at that index.
     */
    public VoteRecord getRecord(int index) {
        return this.records.get(index);
    }

    /**
     * Retrieves the first record in the list.
     *
     * @return The first VoteRecord, or null if empty.
     */
    public VoteRecord getFirstRecord() {
        if (this.records.isEmpty()) {
            return null;
        }
        return this.records.get(0);
    }

    /**
     * Retrieves the last record in the list.
     *
     * @return The last VoteRecord, or null if empty.
     */
    public VoteRecord getLastRecord() {
        if (this.records.isEmpty()) {
            return null;
        }
        return this.records.get(this.records.size() - 1);
    }

    /**
     * Finds all records for a specific election.
     *
     * @param electionId The election ID.
     * @return List of matching records.
     */
    public List<VoteRecord> findByElection(Long electionId) {
        List<VoteRecord> result = new ArrayList<>();

        for (int i = 0; i < this.records.size(); i++) {
            VoteRecord record = this.records.get(i);
            if (record.getElectionId().equals(electionId)) {
                result.add(record);
            }
        }

        return result;
    }

    /**
     * Finds all records for a specific user.
     *
     * @param userId The user ID.
     * @return List of matching records.
     */
    public List<VoteRecord> findByUser(Long userId) {
        List<VoteRecord> result = new ArrayList<>();

        for (int i = 0; i < this.records.size(); i++) {
            VoteRecord record = this.records.get(i);
            if (record.getUserId().equals(userId)) {
                result.add(record);
            }
        }

        return result;
    }

    /**
     * Finds a record by its vote hash.
     *
     * @param voteHash The hash string.
     * @return The VoteRecord if found, null otherwise.
     */
    public VoteRecord findByHash(String voteHash) {
        for (int i = 0; i < this.records.size(); i++) {
            VoteRecord record = this.records.get(i);
            if (record.getVoteHash().equals(voteHash)) {
                return record;
            }
        }
        return null;
    }

    /**
     * Retrieves all records currently in the list.
     *
     * @return List of all VoteRecords.
     */
    public List<VoteRecord> getAllRecords() {
        List<VoteRecord> result = new ArrayList<>();
        for (int i = 0; i < this.records.size(); i++) {
            result.add(this.records.get(i));
        }
        return result;
    }

    /**
     * Finds records within a specific date range.
     *
     * @param start The start timestamp (inclusive).
     * @param end   The end timestamp (inclusive).
     * @return List of matching records.
     */
    public List<VoteRecord> findByDateRange(LocalDateTime start, LocalDateTime end) {
        List<VoteRecord> result = new ArrayList<>();

        for (int i = 0; i < this.records.size(); i++) {
            VoteRecord record = this.records.get(i);
            LocalDateTime timestamp = record.getTimestamp();

            if (!timestamp.isBefore(start) && !timestamp.isAfter(end)) {
                result.add(record);
            }
        }

        return result;
    }

    /**
     * Returns the total number of records.
     *
     * @return list size.
     */
    public int size() {
        return this.records.size();
    }

    /**
     * Checks if the list is empty.
     *
     * @return true if empty.
     */
    public boolean isEmpty() {
        return this.records.isEmpty();
    }

    /**
     * Retrieves statistics about the records.
     *
     * @return RecordStatistics object.
     */
    public RecordStatistics getStatistics() {
        if (this.records.isEmpty()) {
            return new RecordStatistics(0, null, null, 0);
        }

        VoteRecord first = getFirstRecord();
        VoteRecord last = getLastRecord();

        int verifiedCount = 0;
        for (int i = 0; i < this.records.size(); i++) {
            if (this.records.get(i).isVerified()) {
                verifiedCount++;
            }
        }

        return new RecordStatistics(this.records.size(), first.getTimestamp(), last.getTimestamp(), verifiedCount);
    }

    @Data
    @AllArgsConstructor
    public static class RecordStatistics {
        private int totalRecords;
        private LocalDateTime firstRecordTime;
        private LocalDateTime lastRecordTime;
        private int verifiedRecords;
    }
}

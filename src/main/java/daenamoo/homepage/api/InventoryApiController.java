package daenamoo.homepage.api;

import daenamoo.homepage.domain.Inventory;
import daenamoo.homepage.dto.request.CreateInventoryRequestDto;
import daenamoo.homepage.dto.request.UpdateInventoryRequestDto;
import daenamoo.homepage.dto.response.InventoryResponseDto;
import daenamoo.homepage.exception.InventoryNotFoundException;
import daenamoo.homepage.service.InventoryService;
import daenamoo.homepage.service.StudyService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static daenamoo.homepage.api.ResultDto.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventories")
public class InventoryApiController {

    private final InventoryService inventoryService;
    private final StudyService studyService;

    @Operation(method = "POST",
            summary = "스터디 정리본 생성 API",
            description = "CreateInventoryRequestDto 형태로 memberId, studyId, week, content 를 RequestBody에 담아서 요청합니다.")
    @PostMapping("/new")
    public ResponseEntity<String> createInventory(@Valid @RequestBody CreateInventoryRequestDto createInventoryRequestDto) {
        try {
            inventoryService.createInventory(createInventoryRequestDto);
            return new ResponseEntity<>("스터디 정리본이 생성되었습니다.", HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(method = "GET",
            summary = "스터디 정리본 목록 조회 API",
            description = "header에 accessToken을 넣어 요청하면 Result 형태로 응답합니다.")
    @GetMapping("")
    public ResponseEntity<?> readInventories() {
        try {
            List<Inventory> inventories = inventoryService.findInventories();
            List<InventoryResponseDto> collect = inventories.stream()
                    .map(i -> new InventoryResponseDto(i))
                    .collect(Collectors.toList());

            Result result = new Result(collect);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("스터디 정리본 목록 조회에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(method = "GET",
            summary = "스터디 정리본 조회 API",
            description = "header에 accessToken을 넣어 요청하면 Result 형태로 응답합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<?> readInventory(@PathVariable("id") Long id) {
        try {
            Inventory inventory = inventoryService.findInventory(id);
            if (inventory == null) {
                throw new InventoryNotFoundException("스터디 정리본 아이디: " + id + " 를 찾을 수 없습니다.");
            }

            InventoryResponseDto inventoryResponseDto = new InventoryResponseDto(inventory);
            Result result = new Result(inventoryResponseDto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InventoryNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 ID의 스터디 정리본을 찾을 수 없습니다.");
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("스터디 정리본 조회에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(method = "GET",
            summary = "특정 스터디의 스터디 정리본 목록 조회 API",
            description = "header에 accessToken을 넣어 요청하면 Result 형태로 응답합니다.")
    @GetMapping("/studies/{id}")
    public ResponseEntity<?> readInventoryInStudy(@PathVariable("id") Long id) {
        try {
            List<Inventory> inventories = inventoryService.findInventoriesInStudy(id);
            List<InventoryResponseDto> collect = inventories.stream()
                    .map(i -> new InventoryResponseDto(i))
                    .collect(Collectors.toList());

            Result result = new Result(collect);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("특정 스터디의 스터디 정리본 목록 조회에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(method = "GET",
            summary = "특정 스터디의 스터디 정리본 목록 조회 API",
            description = "header에 accessToken을 넣어 요청하면 Result 형태로 응답합니다.")
    @GetMapping("/members/{id}")
    public ResponseEntity<?> readInventoryByMember(@PathVariable("id") Long id) {
        try {
            List<Inventory> inventories = inventoryService.findInventoriesInMember(id);
            List<InventoryResponseDto> collect = inventories.stream()
                    .map(i -> new InventoryResponseDto(i))
                    .collect(Collectors.toList());

            Result result = new Result(collect);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("특정 멤버의 스터디 정리본 목록 조회에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(method = "GET",
            summary = "특정 스터디의 스터디 정리본 목록 조회 API",
            description = "header에 accessToken을 넣어 요청하면 Result 형태로 응답합니다.")
    @GetMapping("/studies/{studyId}/members/{memberId}")
    public ResponseEntity<?> readInventoryInStudyByMember(
            @PathVariable("studyId") Long studyId,
            @PathVariable("memberId") Long memberId
    ) {
        try {
            List<Inventory> inventories = inventoryService.findInventoriesInStudyByMember(studyId, memberId);
            List<InventoryResponseDto> collect = inventories.stream()
                    .map(i -> new InventoryResponseDto(i))
                    .collect(Collectors.toList());

            Result result = new Result(collect);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>("특정 스터디의 특정 멤버의 스터디 정리본 목록 조회에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(method = "PATCH",
            summary = "스터디 정리본 수정 API",
            description = "header에 accessToken을 넣어 요청하면 Result 형태로 응답합니다.")
    @PatchMapping("/{id}")
    public ResponseEntity<String> updateInventory(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateInventoryRequestDto updateInventoryRequestDto
    ) {
        try {
            Inventory inventory = inventoryService.findInventory(id);
            if (inventory == null) {
                throw new InventoryNotFoundException("스터디 정리본 아이디: " + id + " 를 찾을 수 없습니다.");
            }

            inventoryService.updateInventory(id, updateInventoryRequestDto);
            return ResponseEntity.status(HttpStatus.OK).body("스터디 정리본 수정에 성공했습니다.");
        } catch (InventoryNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 ID의 스터디 정리본을 찾을 수 없습니다.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("스터디 정리본 수정에 실패했습니다.");
        }
    }

    @Operation(method = "DELETE",
            summary = "스터디 정리본 삭제 API",
            description = "header에 accessToken을 넣어 요청하면 Result 형태로 응답합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable("id") Long id) {
        try {
            Inventory inventory = inventoryService.findInventory(id);
            if (inventory == null) {
                throw new InventoryNotFoundException("스터디 정리본 아이디: " + id + " 를 찾을 수 없습니다.");
            }

            inventoryService.deleteInventory(id);
            return ResponseEntity.status(HttpStatus.OK).body("스터디 정리본 삭제에 성공했습니다.");
        } catch (InventoryNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("해당 ID의 스터디 정리본을 찾을 수 없습니다.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("스터디 정리본 삭제에 실패했습니다.");
        }
    }
}

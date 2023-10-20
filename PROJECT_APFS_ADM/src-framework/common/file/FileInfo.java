package common.file;

import common.util.FileUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Program Name     : FileInfo
 * Description      :
 * Programmer Name  : ntarget
 * Creation Date    : 2021-02-08
 * Used Table       :
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo {

    private String fileNo; // 파일번호
    private String fileName; // 파일명칭
    private String saveName; // 파일저장명칭
    private String filePath; // 파일경로
    private String gsUserNo; // 등록자번호

    private String fileExt;  // 파일확장자
    private long   fileSize; // 파일크기
    private int    fileIdx;  // 파일표시순서

    private String fileYn;   // 파일첨부여부
    private String needYn;   // 파일필수여부
    private String contType; // 파일컨텐츠타입

    private String fileType; // REFERENCE TYPE
    private String docuCd;   // REFERENCE KEY 값
    private String docuNo;   // REFERENCE KEY 값
    private String docuSeq;  // REFERENCE KEY 값

    private String fileSe;   // REFERENCE KEY 값
    private String papeCd;   // REFERENCE KEY 값
    private String sttsCd;   // REFERENCE KEY 값
    private String docuCn;   // REFERENCE KEY 값
    private String docuYn;   // REFERENCE KEY 값

    private String group;    // 파일그룹

    private String fullPath;
    private String fullFile;

    private boolean deleted; // 삭제대상여부
    
    // 다운로드 파일 전체경로 반환
    public String buildDownFile() {
    	return FileUtils.mergePath(fullPath, saveName);
    }
}

package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
public class InfoController {

    @Value("${server.port}")
    private String port;

    @GetMapping(path = "getPort")
    @Operation(summary = "getPort",
            description = "Get app's port",
            responses = {
                    @ApiResponse(content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class))),
            })
    public ResponseEntity<String> getPort() {
        return ResponseEntity.ok(port);
    }

}

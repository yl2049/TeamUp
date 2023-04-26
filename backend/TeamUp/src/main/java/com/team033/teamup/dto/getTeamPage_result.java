package com.team033.teamup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class getTeamPage_result {
    private String team_id;
    private String team_size;
    private String usersInfo;
}

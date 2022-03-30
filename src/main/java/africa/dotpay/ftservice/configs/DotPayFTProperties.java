/** Created by
 * @author Ayomide.Akinrotoye 
 *  on Mar 30, 2022
 */
package africa.dotpay.ftservice.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

/**
 * @author Ayomide.Akinrotoye
 *
 */

@Configuration
@ConfigurationProperties(prefix = "dotpay.config")
@Validated
@Data
public class DotPayFTProperties {
	private Double commissionPercent;
	private Double feePercent;
	private Double feeAmountCap;
	private String cronForRunningAnalysis;
	private String cronForRunningSummary;
}

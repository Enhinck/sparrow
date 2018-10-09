package com.enhinck.sparrow.common.config;

import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * spring admin中info信息修改
 * 
 * @author hueb
 *
 */
@Component
@Slf4j
public class AdminInfoContributorConfig implements InfoContributor {

	@Autowired
	ProjectInfoProperties projectInfoProperties;

	/**
	 * git打包配置信息见classpath:git.properties
	 */
	public static final String GIT_COMMIT_TIME = "git.commit.time";
	public static final String GIT_COMMIT_MESSAGE = "git.commit.message.full";
	public static final String GIT_COMMIT_ID = "git.commit.id.abbrev";
	public static final String GIT_BRANCH = "git.branch";

	/**
	 * build打包配置信息见classpath:META-INF/build-info.properties
	 */
	public static final String BUILD_TIME = "build.time";
	public static final String BUILD_ARTIFACT = "build.artifact";
	public static final String BUILD_GROUP = "build.group";
	public static final String BUILD_NAME = "build.name";
	public static final String BUILD_VERSION = "build.version";

	@Override
	public void contribute(Info.Builder builder) {
		Map<String, Object> details = new LinkedHashMap<>();
		buildInfo(details);
		gitInfo(details);

		builder.withDetails(details);
	}

	private void buildInfo(Map<String, Object> infos) {
		Properties source = null;
		try {
			source = PropertiesLoaderUtils.loadProperties(projectInfoProperties.getBuild().getLocation());
			String timeStr = (String) source.get(BUILD_TIME);
			String artifact = (String) source.get(BUILD_ARTIFACT);
			String group = (String) source.get(BUILD_GROUP);
			String name = (String) source.get(BUILD_NAME);
			String version = (String) source.get(BUILD_VERSION);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr = dateFormat.format(format.parse(timeStr));
			Map<String, Object> buildInfos = new LinkedHashMap<>();
			buildInfos.put("version", version);
			buildInfos.put("artifact", artifact);
			buildInfos.put("name", name);
			buildInfos.put("group", group);
			buildInfos.put("time", dateStr);
			// 重新定义build内容
			infos.put("build", buildInfos);

		} catch (Exception e) {
			log.info("info生成错误", e.getMessage());
		}
	}

	private void gitInfo(Map<String, Object> infos) {
		Properties source = null;
		try {
			source = PropertiesLoaderUtils.loadProperties(projectInfoProperties.getGit().getLocation());
			String gitBuidTimeStr = (String) source.get(GIT_COMMIT_TIME);
			String commitMessage = (String) source.get(GIT_COMMIT_MESSAGE);
			String id = (String) source.get(GIT_COMMIT_ID);
			String branch = (String) source.get(GIT_BRANCH);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateStr = dateFormat.format(format.parse(gitBuidTimeStr));
			Map<String, Object> gitInfos = new LinkedHashMap<>();

			Map<String, Object> commits = new LinkedHashMap<>();
			commits.put("time", dateStr);
			commits.put("message", commitMessage);
			commits.put("id", id);
			gitInfos.put("commit", commits);
			gitInfos.put("branch", branch);
			// 重新定义git内容
			infos.put("git", gitInfos);
		} catch (Exception e) {
			log.info("info生成错误", e.getMessage());
		}
	}

}

package laser.util.oldCofhCompatibility;

import cofh.core.util.SocialRegistry;

public class OldCofhCompatibility {

	public static boolean playerHasAccess(String paramString1, String paramString2) {
		return (paramString1 != null) && (paramString2 != null)
				&& ((paramString1.toLowerCase().matches(paramString2.toLowerCase()))
						|| (SocialRegistry.friendConf.hasCategory(paramString2.toLowerCase())))
				&& (SocialRegistry.friendConf.getCategory(paramString2.toLowerCase()).containsKey(paramString1.toLowerCase()));
	}

}

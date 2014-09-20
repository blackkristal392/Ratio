package fr.blackkristal392.ratio;

import java.io.File;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Principal extends JavaPlugin {

	public Logger log = Logger.getLogger("Minecraft");

	public String prefix = "[Ratio] ";

	public FileConfiguration config;

	private Object newConfig;

	public void onEnable()
	{
		getLogger().info("Plugin démarré !");
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new RatioListener(this), this);
		File config = new File(getDataFolder(), "config.yml");
		if (!config.exists()) {
			getConfig().options().header("Ratio Config");
			String node = "players";
			getConfig().set(node, null);

			getConfig().createSection(node);
			getConfig().options().copyDefaults(true);
			saveConfig();
		}
	}
	
	
	@Override
     public FileConfiguration getConfig() {
		if (newConfig == null) {
         }
       return (FileConfiguration) newConfig;
    }

	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
	{
		Player p = (Player)sender;

		if(commandLabel.equalsIgnoreCase("ratio"))
		{
			if(args.length == 0)
			{
				int nbKill = this.getConfig().getInt("players." + p.getName() + ".kill");
				int nbDeath = this.getConfig().getInt("players." + p.getName() + ".death");

				if(nbDeath != 0)
				{
					double ratio = Math.round(((double)nbKill/(double)nbDeath) * 100.0D) / 100.0D;					
					p.sendMessage(ChatColor.AQUA + "Statistiques de " + ChatColor.RED + p.getName());
					p.sendMessage(ChatColor.AQUA + "Nombre de kill: " + ChatColor.GREEN + nbKill);
					p.sendMessage(ChatColor.AQUA + "Nombre de death: " + ChatColor.RED + nbDeath);
					p.sendMessage(ChatColor.BLUE + "Ratio: " + ChatColor.RED + ratio);
				}

				else
				{
					p.sendMessage(ChatColor.AQUA + "Statistiques de " + ChatColor.RED + p.getName());
					p.sendMessage(ChatColor.AQUA + "Nombre de kill: " + ChatColor.GREEN + nbKill);
					p.sendMessage(ChatColor.AQUA + "Nombre de death: " + ChatColor.RED + nbDeath);
					p.sendMessage(ChatColor.BLUE + "Ratio: " + ChatColor.RED + nbKill);
				}
				
			}
			
			if(args.length == 1)
			{
				String cmd = args[0];
				
				if(cmd.equalsIgnoreCase("help"))
				{
					p.sendMessage(ChatColor.AQUA + "--------- Ratio Help ---------");
					p.sendMessage(ChatColor.GREEN + "/ratio -> Voir votre ratio");
					p.sendMessage(ChatColor.GOLD + "/ratio <player> -> Voir le ratio du joueur choisi (il doit être connecté)");
					p.sendMessage(ChatColor.GOLD + "Votre ratio est le nombre de personnes que vous avez tué sur le nombre de fois que vous avez été tué !");
				}
				
				else
				{
					Player p2 = this.getServer().getPlayer(args[0]);
					
					if(p2 instanceof Player)
					{
						int nbKill = this.getConfig().getInt("players." + p2.getName() + ".kill");
						int nbDeath = this.getConfig().getInt("players." + p2.getName() + ".death");
						
						if(nbDeath != 0)
						{
							double ratio = Math.round(((double)nbKill/(double)nbDeath) * 100.0D) / 100.0D;
							//BigDecimal ratio = new BigDecimal(nbKill/nbDeath);
							//ratio = ratio.setScale(2, 4);
							
							p.sendMessage(ChatColor.AQUA + "Statistiques de " + ChatColor.RED + p.getName());
							p.sendMessage(ChatColor.AQUA + "Nombre de kill: " + ChatColor.GREEN + nbKill);
							p.sendMessage(ChatColor.AQUA + "Nombre de death: " + ChatColor.RED + nbDeath);
							p.sendMessage(ChatColor.BLUE + "Ratio: " + ChatColor.RED + ratio);
						}

						else
						{
							p.sendMessage(ChatColor.AQUA + "Statistiques de " + ChatColor.RED + p.getName());
							p.sendMessage(ChatColor.AQUA + "Nombre de kill: " + ChatColor.GREEN + nbKill);
							p.sendMessage(ChatColor.AQUA + "Nombre de death: " + ChatColor.RED + nbDeath);
							p.sendMessage(ChatColor.BLUE + "Ratio: " + ChatColor.RED + nbKill);
						}
					}
					
					else
					{
						p.sendMessage(ChatColor.RED + "Ce joueur n'existe pas !");
					}
				}
			}

			return true;
		}

		return false;
	}

} 

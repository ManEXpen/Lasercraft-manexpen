package laser;

import java.io.File;

import cofh.core.CoFHProps;
import cofh.core.item.ItemBase;
import cofh.core.item.ItemBucket;
import cofh.core.network.PacketHandler;
import cofh.core.util.ConfigHandler;
import cofh.core.util.CoreUtils;
import cofh.core.util.fluid.BucketHandler;
import cofh.lib.util.helpers.ItemHelper;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import laser.block.BlockFrame;
import laser.block.BlockGlass;
import laser.block.BlockGoogle;
import laser.block.BlockOre;
import laser.block.BlockStorage;
import laser.block.BlockUTNT;
import laser.block.ItemBlockFrame;
import laser.block.ItemBlockGlass;
import laser.block.ItemBlockGoogle;
import laser.block.ItemBlockOre;
import laser.block.ItemBlockStorage;
import laser.block.ItemBlockUTNT;
import laser.block.machine.BlockMachine;
import laser.block.machine.ItemBlockMachine;
import laser.block.machine.TileAlloySmelter;
import laser.block.machine.TileChunkQuarry;
import laser.block.machine.large.BlockCasing;
import laser.block.machine.large.BlockLargeMachine;
import laser.block.machine.large.ItemBlockCasing;
import laser.block.machine.large.ItemBlockLargeMachine;
import laser.block.machine.large.TileCasing;
import laser.block.machine.large.TileGrinder;
import laser.enchantment.EnchantmentFate;
import laser.enchantment.EnchantmentOverload;
import laser.entity.EntityLaser;
import laser.entity.EntityUTNTPrimed;
import laser.fluid.BlockFluidGhast;
import laser.gui.CreativeTabBlocks;
import laser.gui.CreativeTabItems;
import laser.gui.CreativeTabResources;
import laser.gui.GuiHandler;
import laser.item.ItemFood;
import laser.item.tool.ItemChunkUtil;
import laser.item.tool.ItemRockcutter;
import laser.item.tool.ItemSword;
import laser.network.PacketLCBase;
import laser.proxy.CommonProxy;
import laser.render.entity.RenderLaser;
import laser.render.entity.RenderUTNTPrimed;
import laser.util.crafting.AlloySmelterManager;
import laser.util.crafting.GrinderManager;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import thermalexpansion.block.cache.BlockCache;
import thermalexpansion.item.TEItems;
import thermalexpansion.util.crafting.CrucibleManager;
import thermalexpansion.util.crafting.PulverizerManager;
import thermalexpansion.util.crafting.SmelterManager;
import thermalexpansion.util.crafting.TransposerManager;
import thermalfoundation.fluid.TFFluids;
import thermalfoundation.item.TFItems;

@Mod(
   modid = "LaserCraft",
   name = "LaserCraft",
   version = "1.7.10-0.2B4",
   acceptedMinecraftVersions = "[1.7.10,)",
   guiFactory = "laser.gui.GuiConfigLCFactory",
   dependencies = "required-after:ThermalExpansion"
)
public class Laser {

   public static final String modId = "LaserCraft";
   public static final String modName = "LaserCraft";
   public static final String modVersion = "1.7.10-0.2B4";
   @Instance("LaserCraft")
   public static Laser instance;
   @SidedProxy(
      clientSide = "laser.proxy.ClientProxy",
      serverSide = "laser.proxy.CommonProxy"
   )
   public static CommonProxy proxy;
   public static ConfigHandler config = new ConfigHandler("1.7.10-0.2B4");
   public static File worldGen;
   public static int[] renderIDs = new int[1];
   public static Block ore;
   public static Block storage;
   public static Block blockFluidGhast;
   public static Block generator;
   public static Block machine;
   public static Block glass;
   public static Block frame;
   public static Block casing;
   public static Block machineLarge;
   public static Block google;
   public static Block utnt;
   public static ItemStack oreZinc;
   public static ItemStack oreBauxite;
   public static ItemStack oreTitanium;
   public static ItemStack oreChromium;
   public static ItemStack oreInfused;
   public static ItemStack oreTungsten;
   public static ItemStack oreRuby;
   public static ItemStack oreSapphire;
   public static ItemStack blockZinc;
   public static ItemStack blockBrass;
   public static ItemStack blockAluminium;
   public static ItemStack blockTitanium;
   public static ItemStack blockChromium;
   public static ItemStack blockSteel;
   public static ItemStack blockStainlessSteel;
   public static ItemStack blockGhastly;
   public static ItemStack blockTungsten;
   public static ItemStack blockUnobtainium;
   public static ItemStack blockRuby;
   public static ItemStack blockSapphire;
   public static ItemStack blockBediron;
   public static ItemStack generatorCombustion;
   public static ItemStack generatorSolar;
   public static ItemStack generatorKinetic;
   public static ItemStack generatorHydroelectric;
   public static ItemStack generatorCreaturePowered;
   public static ItemStack generatorGhast;
   public static ItemStack machineAlloySmelter;
   public static ItemStack machineChunkQuarry;
   public static ItemStack machinePump;
   public static ItemStack glassReinforced;
   public static ItemStack glassResonant;
   public static ItemStack mirror;
   public static ItemStack halfMirror;
   public static ItemStack glassLightShielding;
   public static ItemStack frameSteel;
   public static ItemStack casingSteel;
   public static ItemStack machineGrinder;
   public static ItemStack blockGoogle;
   public static ItemStack underwaterTNT;
   public static ItemBase material;
   public static ItemBase chunkUtil;
   public static ItemBase food;
   public static ItemBase rockcutter;
   public static ItemBase sword;
   public static ItemBucket bucket;
   public static ItemStack dustZinc;
   public static ItemStack dustBrass;
   public static ItemStack dustAluminium;
   public static ItemStack dustTitanium;
   public static ItemStack dustChromium;
   public static ItemStack dustSteel;
   public static ItemStack dustStainlessSteel;
   public static ItemStack dustGhastly;
   public static ItemStack dustTungsten;
   public static ItemStack dustUnobtainium;
   public static ItemStack dustBediron;
   public static ItemStack ingotZinc;
   public static ItemStack ingotBrass;
   public static ItemStack ingotAluminium;
   public static ItemStack ingotTitanium;
   public static ItemStack ingotChromium;
   public static ItemStack ingotSteel;
   public static ItemStack ingotStainlessSteel;
   public static ItemStack ingotGhastly;
   public static ItemStack ingotTungsten;
   public static ItemStack ingotUnobtainium;
   public static ItemStack ingotBediron;
   public static ItemStack nuggetZinc;
   public static ItemStack nuggetBrass;
   public static ItemStack nuggetAluminium;
   public static ItemStack nuggetTitanium;
   public static ItemStack nuggetChromium;
   public static ItemStack nuggetSteel;
   public static ItemStack nuggetStainlessSteel;
   public static ItemStack nuggetGhastly;
   public static ItemStack nuggetTungsten;
   public static ItemStack nuggetUnobtainium;
   public static ItemStack nuggetBediron;
   public static ItemStack gearZinc;
   public static ItemStack gearBrass;
   public static ItemStack gearAluminium;
   public static ItemStack gearTitanium;
   public static ItemStack gearChromium;
   public static ItemStack gearSteel;
   public static ItemStack gearStainlessSteel;
   public static ItemStack gearGhastly;
   public static ItemStack gearTungsten;
   public static ItemStack gearUnobtainium;
   public static ItemStack gearBediron;
   public static ItemStack dustInfused;
   public static ItemStack tentacleGhast;
   public static ItemStack crystalGhast;
   public static ItemStack gemInfused;
   public static ItemStack gemRuby;
   public static ItemStack gemSapphire;
   public static ItemStack lensRuby;
   public static ItemStack lensSapphire;
   public static ItemStack laserOscillator;
   public static ItemStack pulseLaserOscillator;
   public static ItemStack dynamicLaserOscillator;
   public static ItemStack dustBedrock;
   public static ItemStack bladeSteel;
   public static ItemStack ingotGoogle;
   public static ItemStack chunkReader;
   public static ItemStack chunkRecorder;
   public static ItemStack fleshGhastly;
   public static ItemStack normalRockcutter;
   public static ItemStack laserRockcutter;
   public static ItemStack swordBediron;
   public static ItemStack bucketGhast;
   public static Fluid fluidGhast;
   public static EnumEnchantmentType enchantmentTypeLC = EnumHelper.addEnchantmentType("laserCraft");
   public static Enchantment enchantmentFate;
   public static Enchantment enchantmentOverload;
   public static CreativeTabs tabResources = new CreativeTabResources();
   public static CreativeTabs tabBlocks = new CreativeTabBlocks();
   public static CreativeTabs tabItems = (new CreativeTabItems()).func_111229_a(new EnumEnchantmentType[]{enchantmentTypeLC});


   @EventHandler
   public void preInit(FMLPreInitializationEvent event) {
      config.setConfiguration(new Configuration(new File(event.getModConfigurationDirectory(), "cofh/LaserCraft.cfg")));
      this.cleanConfig(true);
      this.configure();
      fluidGhast = (new Fluid("ghast")).setLuminosity(7).setDensity(1500).setViscosity(1000).setTemperature(350).setRarity(EnumRarity.uncommon);
      FluidRegistry.registerFluid(fluidGhast);
      ore = new BlockOre();
      storage = new BlockStorage();
      blockFluidGhast = new BlockFluidGhast();
      machine = new BlockMachine();
      glass = new BlockGlass();
      frame = new BlockFrame();
      casing = new BlockCasing();
      machineLarge = new BlockLargeMachine();
      google = (new BlockGoogle()).setBlockTextureName("laser:google/BlockGoogle");
      utnt = new BlockUTNT();
      GameRegistry.registerBlock(ore, ItemBlockOre.class, "ore");
      GameRegistry.registerBlock(storage, ItemBlockStorage.class, "storage");
      GameRegistry.registerBlock(blockFluidGhast, "blockFluidGhast");
      GameRegistry.registerBlock(machine, ItemBlockMachine.class, "machine");
      GameRegistry.registerBlock(glass, ItemBlockGlass.class, "glass");
      GameRegistry.registerBlock(frame, ItemBlockFrame.class, "frame");
      GameRegistry.registerBlock(casing, ItemBlockCasing.class, "casing");
      GameRegistry.registerBlock(machineLarge, ItemBlockLargeMachine.class, "machineLarge");
      GameRegistry.registerBlock(google, ItemBlockGoogle.class, "google");
      GameRegistry.registerBlock(utnt, ItemBlockUTNT.class, "utnt");
      material = (ItemBase)(new ItemBase("laser")).func_77655_b("material").setCreativeTab(tabResources);
      bucket = (ItemBucket)(new ItemBucket("laser")).func_77655_b("bucket").setCreativeTab(tabResources);
      chunkUtil = new ItemChunkUtil();
      food = new ItemFood();
      rockcutter = new ItemRockcutter();
      sword = new ItemSword();
      if(getNextEnchantmentID() != -1) {
         enchantmentFate = new EnchantmentFate(getNextEnchantmentID(), 10);
      }

      if(getNextEnchantmentID() != -1) {
         enchantmentOverload = new EnchantmentOverload(getNextEnchantmentID(), 10);
      }

      EntityRegistry.registerGlobalEntityID(EntityUTNTPrimed.class, "UTNTPrimed", EntityRegistry.findGlobalUniqueEntityId());
      EntityRegistry.registerModEntity(EntityUTNTPrimed.class, "UTNTPrimed", 1, instance, 160, 10, true);
   }

   @EventHandler
   public void init(FMLInitializationEvent event) {
      worldGen = new File(CoFHProps.configDir, "/cofh/world/LaserCraft-Ores.json");
      if(!worldGen.exists()) {
         try {
            worldGen.createNewFile();
            CoreUtils.copyFileUsingStream("assets/laser/world/LaserCraft-Ores.json", worldGen);
         } catch (Throwable var3) {
            var3.printStackTrace();
         }
      }

      for(int i = 0; i < renderIDs.length; ++i) {
         renderIDs[i] = proxy.getNewRenderType();
      }

      proxy.registerRenderers();
      RenderingRegistry.registerEntityRenderingHandler(EntityLaser.class, new RenderLaser());
      RenderingRegistry.registerEntityRenderingHandler(EntityUTNTPrimed.class, new RenderUTNTPrimed());
      NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
      GameRegistry.registerTileEntity(TileAlloySmelter.class, "laser.AlloySmelter");
      GameRegistry.registerTileEntity(TileChunkQuarry.class, "laser.ChunkQuarry");
      GameRegistry.registerTileEntity(TileCasing.class, "laser.Casing");
      GameRegistry.registerTileEntity(TileGrinder.class, "laser.Grinder");
      TileAlloySmelter.initialize();
      TileChunkQuarry.initialize();
      TileGrinder.initialize();
      PacketHandler.instance.registerPacket(PacketLCBase.class);
      MinecraftForge.EVENT_BUS.register(new laser.util.EventHandler());
      oreZinc = new ItemStack(ore, 1, 0);
      oreBauxite = new ItemStack(ore, 1, 1);
      oreTitanium = new ItemStack(ore, 1, 2);
      oreChromium = new ItemStack(ore, 1, 3);
      oreInfused = new ItemStack(ore, 1, 4);
      oreTungsten = new ItemStack(ore, 1, 5);
      oreRuby = new ItemStack(ore, 1, 6);
      oreSapphire = new ItemStack(ore, 1, 7);
      blockZinc = new ItemStack(storage, 1, 0);
      blockBrass = new ItemStack(storage, 1, 1);
      blockAluminium = new ItemStack(storage, 1, 2);
      blockTitanium = new ItemStack(storage, 1, 3);
      blockChromium = new ItemStack(storage, 1, 4);
      blockSteel = new ItemStack(storage, 1, 5);
      blockStainlessSteel = new ItemStack(storage, 1, 6);
      blockGhastly = new ItemStack(storage, 1, 7);
      blockTungsten = new ItemStack(storage, 1, 8);
      blockUnobtainium = new ItemStack(storage, 1, 9);
      blockRuby = new ItemStack(storage, 1, 10);
      blockSapphire = new ItemStack(storage, 1, 11);
      blockBediron = new ItemStack(storage, 1, 12);
      machineAlloySmelter = BlockMachine.setStackTag(new ItemStack(machine, 1, 0));
      machineChunkQuarry = BlockMachine.setStackTag(new ItemStack(machine, 1, 1));
      glassReinforced = new ItemStack(glass, 1, 0);
      glassResonant = new ItemStack(glass, 1, 1);
      mirror = new ItemStack(glass, 1, 2);
      halfMirror = new ItemStack(glass, 1, 3);
      glassLightShielding = new ItemStack(glass, 1, 4);
      frameSteel = new ItemStack(frame, 1, 0);
      casingSteel = new ItemStack(casing, 1, 0);
      machineGrinder = ItemBlockLargeMachine.setDefaultTag(new ItemStack(machineLarge, 1, 0));
      blockGoogle = new ItemStack(google);
      underwaterTNT = new ItemStack(utnt);
      ItemHelper.registerWithHandlers("oreZinc", oreZinc);
      ItemHelper.registerWithHandlers("oreBauxite", oreBauxite);
      ItemHelper.registerWithHandlers("oreTitanium", oreTitanium);
      ItemHelper.registerWithHandlers("oreChromium", oreChromium);
      ItemHelper.registerWithHandlers("oreInfused", oreInfused);
      ItemHelper.registerWithHandlers("oreTungsten", oreTungsten);
      ItemHelper.registerWithHandlers("oreRuby", oreRuby);
      ItemHelper.registerWithHandlers("oreSapphire", oreSapphire);
      ItemHelper.registerWithHandlers("blockZinc", blockZinc);
      ItemHelper.registerWithHandlers("blockBrass", blockBrass);
      ItemHelper.registerWithHandlers("blockAluminium", blockAluminium);
      ItemHelper.registerWithHandlers("blockTitanium", blockTitanium);
      ItemHelper.registerWithHandlers("blockChromium", blockChromium);
      ItemHelper.registerWithHandlers("blockSteel", blockSteel);
      ItemHelper.registerWithHandlers("blockStainlessSteel", blockStainlessSteel);
      ItemHelper.registerWithHandlers("blockGhastly", blockGhastly);
      ItemHelper.registerWithHandlers("blockTungsten", blockTungsten);
      ItemHelper.registerWithHandlers("blockUnobtainium", blockUnobtainium);
      ItemHelper.registerWithHandlers("blockRuby", blockRuby);
      ItemHelper.registerWithHandlers("blockSapphire", blockSapphire);
      ItemHelper.registerWithHandlers("blockBediron", blockBediron);
      GameRegistry.registerCustomItemStack("machineAlloySmelter", machineAlloySmelter);
      GameRegistry.registerCustomItemStack("machineChunkQuarry", machineChunkQuarry);
      ItemHelper.registerWithHandlers("blockGlassReinforced", glassReinforced);
      ItemHelper.registerWithHandlers("blockGlassResonant", glassResonant);
      GameRegistry.registerCustomItemStack("mirror", mirror);
      GameRegistry.registerCustomItemStack("halfMirror", halfMirror);
      ItemHelper.registerWithHandlers("blockGlassLightShielding", glassLightShielding);
      GameRegistry.registerCustomItemStack("frameSteel", frameSteel);
      GameRegistry.registerCustomItemStack("casingSteel", casingSteel);
      GameRegistry.registerCustomItemStack("machineGrinder", machineGrinder);
      GameRegistry.registerCustomItemStack("blockGoogle", blockGoogle);
      GameRegistry.registerCustomItemStack("underwaterTNT", underwaterTNT);
      dustInfused = material.addOreDictItem(128, "dustInfused", 1);
      tentacleGhast = material.addItem(129, "tentacleGhast");
      crystalGhast = material.addItem(130, "crystalGhast", 1);
      gemInfused = material.addOreDictItem(131, "gemInfused", 1);
      gemRuby = material.addOreDictItem(132, "gemRuby");
      gemSapphire = material.addOreDictItem(133, "gemSapphire");
      lensRuby = material.addOreDictItem(134, "lensRuby", 1);
      lensSapphire = material.addOreDictItem(135, "lensSapphire", 1);
      laserOscillator = material.addItem(136, "laserOscillator", 1);
      pulseLaserOscillator = material.addItem(137, "pulseLaserOscillator", 1);
      dynamicLaserOscillator = material.addItem(138, "dynamicLaserOscillator", 2);
      dustBedrock = material.addOreDictItem(139, "dustBedrock", 2);
      bladeSteel = material.addOreDictItem(140, "bladeSteel");
      ingotGoogle = material.addItem(141, "ingotGoogle", 1);
      dustZinc = material.addOreDictItem(0, "dustZinc");
      dustBrass = material.addOreDictItem(1, "dustBrass");
      dustAluminium = material.addOreDictItem(2, "dustAluminium");
      dustTitanium = material.addOreDictItem(3, "dustTitanium", 1);
      dustChromium = material.addOreDictItem(4, "dustChromium", 1);
      dustSteel = material.addOreDictItem(5, "dustSteel");
      dustStainlessSteel = material.addOreDictItem(6, "dustStainlessSteel", 1);
      dustGhastly = material.addOreDictItem(7, "dustGhastly", 3);
      dustTungsten = material.addOreDictItem(8, "dustTungsten", 1);
      dustUnobtainium = material.addOreDictItem(9, "dustUnobtainium", 2);
      dustBediron = material.addOreDictItem(10, "dustBediron", 3);
      ingotZinc = material.addOreDictItem(32, "ingotZinc");
      ingotBrass = material.addOreDictItem(33, "ingotBrass");
      ingotAluminium = material.addOreDictItem(34, "ingotAluminium");
      ingotTitanium = material.addOreDictItem(35, "ingotTitanium", 1);
      ingotChromium = material.addOreDictItem(36, "ingotChromium", 1);
      ingotSteel = material.addOreDictItem(37, "ingotSteel");
      ingotStainlessSteel = material.addOreDictItem(38, "ingotStainlessSteel", 1);
      ingotGhastly = material.addOreDictItem(39, "ingotGhastly", 3);
      ingotTungsten = material.addOreDictItem(40, "ingotTungsten", 1);
      ingotUnobtainium = material.addOreDictItem(41, "ingotUnobtainium", 2);
      ingotBediron = material.addOreDictItem(42, "ingotBediron", 3);
      nuggetZinc = material.addOreDictItem(64, "nuggetZinc");
      nuggetBrass = material.addOreDictItem(65, "nuggetBrass");
      nuggetAluminium = material.addOreDictItem(66, "nuggetAluminium");
      nuggetTitanium = material.addOreDictItem(67, "nuggetTitanium", 1);
      nuggetChromium = material.addOreDictItem(68, "nuggetChromium", 1);
      nuggetSteel = material.addOreDictItem(69, "nuggetSteel");
      nuggetStainlessSteel = material.addOreDictItem(70, "nuggetStainlessSteel", 1);
      nuggetGhastly = material.addOreDictItem(71, "nuggetGhastly", 3);
      nuggetTungsten = material.addOreDictItem(72, "nuggetTungsten", 1);
      nuggetUnobtainium = material.addOreDictItem(73, "nuggetUnobtainium", 2);
      nuggetBediron = material.addOreDictItem(74, "nuggetBediron", 3);
      gearZinc = material.addOreDictItem(96, "gearZinc");
      gearBrass = material.addOreDictItem(97, "gearBrass");
      gearAluminium = material.addOreDictItem(98, "gearAluminium");
      gearTitanium = material.addOreDictItem(99, "gearTitanium", 1);
      gearChromium = material.addOreDictItem(100, "gearChromium", 1);
      gearSteel = material.addOreDictItem(101, "gearSteel");
      gearStainlessSteel = material.addOreDictItem(102, "gearStainlessSteel", 1);
      gearGhastly = material.addOreDictItem(103, "gearGhastly", 3);
      gearTungsten = material.addOreDictItem(104, "gearTungsten", 1);
      gearUnobtainium = material.addOreDictItem(105, "gearUnobtainium", 2);
      gearBediron = material.addOreDictItem(106, "gearBediron", 3);
      bucketGhast = bucket.addItem(0, "bucketGhast", 1);
      chunkReader = chunkUtil.addItem(0, "chunkReader");
      chunkRecorder = chunkUtil.addItem(1, "chunkRecorder", 1);
      fleshGhastly = food.addItem(0, "fleshGhastly");
      normalRockcutter = rockcutter.addItem(0, "normalRockcutter", 1);
      laserRockcutter = rockcutter.addItem(1, "laserRockcutter", 1);
      swordBediron = sword.addItem(0, "swordBediron", 3);
      OreDictionary.registerOre("oreChrome", oreChromium);
      OreDictionary.registerOre("blockAluminum", blockAluminium);
      OreDictionary.registerOre("blockChrome", blockChromium);
      OreDictionary.registerOre("dustAluminum", dustAluminium);
      OreDictionary.registerOre("dustChrome", dustChromium);
      OreDictionary.registerOre("ingotAluminum", ingotAluminium);
      OreDictionary.registerOre("ingotChrome", ingotChromium);
      OreDictionary.registerOre("nuggetAluminum", nuggetAluminium);
      OreDictionary.registerOre("nuggetChrome", nuggetChromium);
      OreDictionary.registerOre("gearAluminum", gearAluminium);
      OreDictionary.registerOre("gearChrome", gearChromium);
      BucketHandler.registerBucket(blockFluidGhast, 0, bucketGhast);
      FluidContainerRegistry.registerFluidContainer(fluidGhast, bucketGhast, FluidContainerRegistry.EMPTY_BUCKET);
   }

   @EventHandler
   public void postInit(FMLPostInitializationEvent event) {
      ItemHelper.addStorageRecipe(blockZinc, "ingotZinc");
      ItemHelper.addStorageRecipe(blockBrass, "ingotBrass");
      ItemHelper.addStorageRecipe(blockAluminium, "ingotAluminium");
      ItemHelper.addStorageRecipe(blockTitanium, "ingotTitanium");
      ItemHelper.addStorageRecipe(blockChromium, "ingotChromium");
      ItemHelper.addStorageRecipe(blockSteel, "ingotSteel");
      ItemHelper.addStorageRecipe(blockStainlessSteel, "ingotStainlessSteel");
      ItemHelper.addStorageRecipe(blockGhastly, "ingotGhastly");
      ItemHelper.addStorageRecipe(blockTungsten, "ingotTungsten");
      ItemHelper.addStorageRecipe(blockUnobtainium, "ingotUnobtainium");
      ItemHelper.addStorageRecipe(blockRuby, "gemRuby");
      ItemHelper.addStorageRecipe(blockSapphire, "gemSapphire");
      ItemHelper.addStorageRecipe(blockBediron, "ingotBediron");
      ItemHelper.addStorageRecipe(blockGoogle, ingotGoogle);
      ItemHelper.addStorageRecipe(ingotZinc, "nuggetZinc");
      ItemHelper.addStorageRecipe(ingotBrass, "nuggetBrass");
      ItemHelper.addStorageRecipe(ingotAluminium, "nuggetAluminium");
      ItemHelper.addStorageRecipe(ingotTitanium, "nuggetTitanium");
      ItemHelper.addStorageRecipe(ingotChromium, "nuggetChromium");
      ItemHelper.addStorageRecipe(ingotSteel, "nuggetSteel");
      ItemHelper.addStorageRecipe(ingotStainlessSteel, "nuggetStainlessSteel");
      ItemHelper.addStorageRecipe(ingotGhastly, "nuggetGhastly");
      ItemHelper.addStorageRecipe(ingotTungsten, "nuggetTungsten");
      ItemHelper.addStorageRecipe(ingotUnobtainium, "nuggetUnobtainium");
      ItemHelper.addStorageRecipe(ingotBediron, "nuggetBediron");
      ItemHelper.addReverseStorageRecipe(ingotZinc, "blockZinc");
      ItemHelper.addReverseStorageRecipe(ingotBrass, "blockBrass");
      ItemHelper.addReverseStorageRecipe(ingotAluminium, "blockAluminium");
      ItemHelper.addReverseStorageRecipe(ingotTitanium, "blockTitanium");
      ItemHelper.addReverseStorageRecipe(ingotChromium, "blockChromium");
      ItemHelper.addReverseStorageRecipe(ingotSteel, "blockSteel");
      ItemHelper.addReverseStorageRecipe(ingotStainlessSteel, "blockStainlessSteel");
      ItemHelper.addReverseStorageRecipe(ingotGhastly, "blockGhastly");
      ItemHelper.addReverseStorageRecipe(ingotTungsten, "blockTungsten");
      ItemHelper.addReverseStorageRecipe(ingotUnobtainium, "blockUnobtainium");
      ItemHelper.addReverseStorageRecipe(gemRuby, "blockRuby");
      ItemHelper.addReverseStorageRecipe(gemSapphire, "blockSapphire");
      ItemHelper.addReverseStorageRecipe(ingotBediron, "blockBediron");
      ItemHelper.addReverseStorageRecipe(ingotGoogle, blockGoogle);
      ItemHelper.addReverseStorageRecipe(nuggetZinc, "ingotZinc");
      ItemHelper.addReverseStorageRecipe(nuggetBrass, "ingotBrass");
      ItemHelper.addReverseStorageRecipe(nuggetAluminium, "ingotAluminium");
      ItemHelper.addReverseStorageRecipe(nuggetTitanium, "ingotTitanium");
      ItemHelper.addReverseStorageRecipe(nuggetChromium, "ingotChromium");
      ItemHelper.addReverseStorageRecipe(nuggetSteel, "ingotSteel");
      ItemHelper.addReverseStorageRecipe(nuggetStainlessSteel, "ingotStainlessSteel");
      ItemHelper.addReverseStorageRecipe(nuggetGhastly, "ingotGhastly");
      ItemHelper.addReverseStorageRecipe(nuggetTungsten, "ingotTungsten");
      ItemHelper.addReverseStorageRecipe(nuggetUnobtainium, "ingotUnobtainium");
      ItemHelper.addReverseStorageRecipe(nuggetBediron, "ingotBediron");
      ItemHelper.addGearRecipe(gearZinc, "ingotZinc");
      ItemHelper.addGearRecipe(gearBrass, "ingotBrass");
      ItemHelper.addGearRecipe(gearAluminium, "ingotAluminium");
      ItemHelper.addGearRecipe(gearTitanium, "ingotTitanium");
      ItemHelper.addGearRecipe(gearChromium, "ingotChromium");
      ItemHelper.addGearRecipe(gearSteel, "ingotSteel");
      ItemHelper.addGearRecipe(gearStainlessSteel, "ingotStainlessSteel");
      ItemHelper.addGearRecipe(gearGhastly, "ingotGhastly");
      ItemHelper.addGearRecipe(gearTungsten, "ingotTungsten");
      ItemHelper.addGearRecipe(gearUnobtainium, "ingotUnobtainium");
      ItemHelper.addGearRecipe(gearBediron, "ingotBediron");
      GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(TFItems.dustMana, 2), new Object[]{"dustObsidian", "crystalCinnabar", "dustRedstone", "dustMithril"}));
      GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustBrass, 4), new Object[]{"dustCopper", "dustCopper", "dustCopper", "dustZinc"}));
      GameRegistry.addRecipe(new ShapelessOreRecipe(dustSteel, new Object[]{"dustCoal", "dustCoal", "dustIron"}));
      GameRegistry.addRecipe(new ShapelessOreRecipe(dustSteel, new Object[]{"dustCharcoal", "dustCharcoal", "dustCharcoal", "dustCharcoal", "dustIron"}));
      GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustStainlessSteel, 6), new Object[]{"dustIron", "dustIron", "dustIron", "dustIron", "dustNickel", "dustChromium"}));
      GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustGhastly, 2), new Object[]{"dustStainlessSteel", "dustStainlessSteel", "dustStainlessSteel", crystalGhast, TFItems.bucketMana}));
      GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustUnobtainium, 2), new Object[]{"dustTitanium", "dustTitanium", "dustTungsten", "dustTungsten", TFItems.bucketCoal}));
      GameRegistry.addRecipe(new ShapelessOreRecipe(ItemHelper.cloneStack(dustBediron, 2), new Object[]{"dustBedrock", "dustIron"}));
      GameRegistry.addRecipe(new ShapedOreRecipe(frameSteel, new Object[]{"SGS", "GAG", "SGS", Character.valueOf('A'), "gearAluminium", Character.valueOf('S'), "ingotSteel", Character.valueOf('G'), "blockGlass"}));
      GameRegistry.addRecipe(new ShapedOreRecipe(ItemHelper.cloneStack(casingSteel, 6), new Object[]{"III", "IGI", "III", Character.valueOf('I'), "ingotSteel", Character.valueOf('G'), "gearSteel"}));
      GameRegistry.addRecipe(new ShapedOreRecipe(machineAlloySmelter, new Object[]{"BBB", "BFB", "ZGZ", Character.valueOf('B'), new ItemStack(Blocks.stonebrick), Character.valueOf('F'), frameSteel, Character.valueOf('Z'), "gearZinc", Character.valueOf('G'), TEItems.powerCoilGold}));
      GameRegistry.addRecipe(new ShapedOreRecipe(machineChunkQuarry, new Object[]{"LLL", "RFC", "ZGZ", Character.valueOf('L'), laserOscillator, Character.valueOf('F'), frameSteel, Character.valueOf('R'), thermalexpansion.block.simple.BlockFrame.frameCellReinforcedFull, Character.valueOf('Z'), "gearZinc", Character.valueOf('C'), BlockCache.cacheHardened, Character.valueOf('G'), TEItems.powerCoilGold}));
      GameRegistry.addRecipe(new ShapedOreRecipe(machineGrinder, new Object[]{"BBB", "PCP", "SGS", Character.valueOf('B'), "bladeSteel", Character.valueOf('P'), Blocks.piston, Character.valueOf('C'), casingSteel, Character.valueOf('S'), "gearStainlessSteel", Character.valueOf('G'), TEItems.powerCoilGold}));
      GameRegistry.addRecipe(new ShapedOreRecipe(laserOscillator, new Object[]{"RHM", "BGB", Character.valueOf('R'), "lensRuby", Character.valueOf('H'), halfMirror, Character.valueOf('M'), mirror, Character.valueOf('B'), "gearBrass", Character.valueOf('G'), Blocks.glowstone}));
      GameRegistry.addRecipe(new ShapedOreRecipe(pulseLaserOscillator, new Object[]{"SHM", "BGB", Character.valueOf('S'), "lensSapphire", Character.valueOf('H'), halfMirror, Character.valueOf('M'), mirror, Character.valueOf('B'), "gearBrass", Character.valueOf('G'), Blocks.glowstone}));
      GameRegistry.addRecipe(new ShapedOreRecipe(dynamicLaserOscillator, new Object[]{"IHM", "SGS", Character.valueOf('I'), "gemInfused", Character.valueOf('H'), halfMirror, Character.valueOf('M'), mirror, Character.valueOf('S'), "gearSteel", Character.valueOf('G'), Blocks.glowstone}));
      GameRegistry.addRecipe(new ShapedOreRecipe(bladeSteel, new Object[]{"I I", " G ", "I I", Character.valueOf('I'), "ingotSteel", Character.valueOf('G'), "gearSteel"}));
      GameRegistry.addRecipe(new ShapedOreRecipe(chunkReader, new Object[]{" R ", "IDI", " G ", Character.valueOf('R'), "dustRedstone", Character.valueOf('I'), "ingotIron", Character.valueOf('G'), "gearIron", Character.valueOf('D'), new ItemStack(Blocks.dirt)}));
      GameRegistry.addRecipe(new ShapedOreRecipe(chunkRecorder, new Object[]{" S ", "GCG", " S ", Character.valueOf('S'), TEItems.powerCoilSilver, Character.valueOf('C'), chunkReader, Character.valueOf('G'), "gearBrass"}));
      GameRegistry.addRecipe(new ShapedOreRecipe(normalRockcutter, new Object[]{"BT ", "TGT", " TC", Character.valueOf('B'), "bladeSteel", Character.valueOf('T'), "ingotTitanium", Character.valueOf('G'), "gearTitanium", Character.valueOf('C'), TEItems.capacitorHardened}));
      GameRegistry.addRecipe(new ShapedOreRecipe(laserRockcutter, new Object[]{"LT ", "TGT", " TC", Character.valueOf('L'), dynamicLaserOscillator, Character.valueOf('T'), "ingotTitanium", Character.valueOf('G'), "gearChromium", Character.valueOf('C'), TEItems.capacitorHardened}));
      GameRegistry.addRecipe(new ShapedOreRecipe(swordBediron, new Object[]{"I", "I", "G", Character.valueOf('I'), "ingotBediron", Character.valueOf('G'), "gearBediron"}));
      GameRegistry.addSmelting(oreZinc, ingotZinc, 0.7F);
      GameRegistry.addSmelting(oreRuby, gemRuby, 1.0F);
      GameRegistry.addSmelting(oreSapphire, gemSapphire, 1.0F);
      GameRegistry.addSmelting(dustZinc, ingotZinc, 0.0F);
      GameRegistry.addSmelting(dustBrass, ingotBrass, 0.0F);
      GameRegistry.addSmelting(dustAluminium, ingotAluminium, 0.0F);
      GameRegistry.addSmelting(dustSteel, ingotSteel, 0.0F);
      GameRegistry.addSmelting(dustStainlessSteel, ingotStainlessSteel, 0.0F);
      short m = 4000;
      PulverizerManager.addOreNameToDustRecipe(m, "oreZinc", dustZinc, TFItems.dustIron, 10);
      PulverizerManager.addOreNameToDustRecipe(m, "oreBauxite", dustAluminium, dustTitanium, 10);
      PulverizerManager.addOreNameToDustRecipe(m, "oreTitanium", dustTitanium, TFItems.dustIron, 10);
      PulverizerManager.addOreNameToDustRecipe(m, "oreChromium", dustChromium, TFItems.dustLead, 10);
      PulverizerManager.addRecipe(m, oreInfused, new ItemStack(Blocks.cobblestone), dustInfused, 100);
      PulverizerManager.addOreNameToDustRecipe(m, "oreTungsten", dustTungsten, TFItems.dustLead, 10);
      m = 2400;
      PulverizerManager.addIngotNameToDustRecipe(m, "ingotZinc", dustZinc);
      PulverizerManager.addIngotNameToDustRecipe(m, "ingotBrass", dustBrass);
      PulverizerManager.addIngotNameToDustRecipe(m, "ingotAluminium", dustAluminium);
      PulverizerManager.addIngotNameToDustRecipe(m, "ingotTitanium", dustTitanium);
      PulverizerManager.addIngotNameToDustRecipe(m, "ingotChromium", dustChromium);
      PulverizerManager.addIngotNameToDustRecipe(m, "ingotSteel", dustSteel);
      PulverizerManager.addIngotNameToDustRecipe(m, "ingotStainlessSteel", dustStainlessSteel);
      PulverizerManager.addIngotNameToDustRecipe(m, "ingotTungsten", dustTungsten);
      PulverizerManager.removeRecipe(dustBediron);
      PulverizerManager.addOreNameToDustRecipe(m, "oreRuby", gemRuby, (ItemStack)null, 0);
      PulverizerManager.addOreNameToDustRecipe(m, "oreSapphire", gemSapphire, (ItemStack)null, 0);
      PulverizerManager.addRecipe(1600, tentacleGhast, ItemHelper.cloneStack(fleshGhastly, 4), crystalGhast, 25);
      SmelterManager.addAlloyRecipe(1600, "dustZinc", 1, "dustCopper", 3, ItemHelper.cloneStack(ingotBrass, 4));
      SmelterManager.addAlloyRecipe(2400, "ingotZinc", 1, "ingotCopper", 3, ItemHelper.cloneStack(ingotBrass, 4));
      SmelterManager.addBlastOreName("titanium");
      SmelterManager.addBlastOreName("chromium");
      SmelterManager.addBlastOreName("tungsten");
      SmelterManager.addAlloyRecipe(8000, new ItemStack(Items.ghast_tear), ItemHelper.cloneStack(dustInfused, 2), crystalGhast);
      SmelterManager.addAlloyRecipe(16000, TFItems.dustMana, dustGhastly, ingotGhastly);
      SmelterManager.addAlloyRecipe(16000, TFItems.dustMana, dustUnobtainium, ingotUnobtainium);
      SmelterManager.addAlloyRecipe(16000, TFItems.dustMana, dustBediron, ingotBediron);
      CrucibleManager.addRecipe(8000, TFItems.dustMana, new FluidStack(TFFluids.fluidMana, 100));
      CrucibleManager.addRecipe(30000, tentacleGhast, new FluidStack(fluidGhast, 500));
      CrucibleManager.addRecipe(20000, crystalGhast, new FluidStack(fluidGhast, 250));
      TransposerManager.addFillRecipe(8000, new ItemStack(Blocks.tnt), underwaterTNT, new FluidStack(TFFluids.fluidPyrotheum, 250), false);
      AlloySmelterManager.addOreDictionaryRecipe(800, ItemHelper.cloneStack(ingotStainlessSteel, 6), new Object[]{"dustIron", Integer.valueOf(4), "dustNickel", "dustChromium"});
      AlloySmelterManager.addOreDictionaryRecipe(1200, ItemHelper.cloneStack(ingotStainlessSteel, 6), new Object[]{"ingotIron", Integer.valueOf(4), "ingotNickel", "ingotChromium"});
      AlloySmelterManager.addOreDictionaryRecipe(8000, glassReinforced, new Object[]{"blockGlassHardened", "dustObsidian", Integer.valueOf(4), "dustElectrum"});
      AlloySmelterManager.addOreDictionaryRecipe(8000, glassResonant, new Object[]{"blockGlassReinforced", "dustObsidian", Integer.valueOf(4), "dustEnderium"});
      AlloySmelterManager.addOreDictionaryRecipe(8000, mirror, new Object[]{"blockGlassColorless", "dustSilver", Integer.valueOf(4)});
      AlloySmelterManager.addOreDictionaryRecipe(8000, halfMirror, new Object[]{"blockGlassColorless", "dustAluminium", Integer.valueOf(2)});
      AlloySmelterManager.addOreDictionaryRecipe(8000, halfMirror, new Object[]{"blockGlassColorless", "dustAluminum", Integer.valueOf(2)});
      AlloySmelterManager.addOreDictionaryRecipe(8000, glassLightShielding, new Object[]{"blockGlassResonant", "dyeBlack", Integer.valueOf(4)});
      AlloySmelterManager.addOreDictionaryRecipe(16000, gemInfused, new Object[]{"gemDiamond", "dustInfused", Integer.valueOf(2), ItemHelper.cloneStack(TFItems.dustPyrotheum, 2)});
      AlloySmelterManager.addOreDictionaryRecipe(32000, lensRuby, new Object[]{"gemRuby", "dustChromium"});
      AlloySmelterManager.addOreDictionaryRecipe(32000, lensRuby, new Object[]{"gemRuby", "dustChrome"});
      AlloySmelterManager.addOreDictionaryRecipe(32000, lensSapphire, new Object[]{"gemSapphire", "dustTitanium"});
      AlloySmelterManager.addOreDictionaryRecipe(4000, ingotGoogle, new Object[]{"ingotChromium", "dyeRed", "dyeGreen", "dyeYellow", "dyeBlue"});
      GrinderManager.addRecipe(16000, new ItemStack(Blocks.bedrock), new ItemStack[]{ItemHelper.cloneStack(dustBedrock, 2)});
   }

   @EventHandler
   public void loadComplete(FMLLoadCompleteEvent paramFMLLoadCompleteEvent) {
      AlloySmelterManager.loadRecipes();
      GrinderManager.loadRecipes();
      GrinderManager.refreshRecipes();
      this.cleanConfig(false);
      config.cleanUp(false, true);
   }

   private void configure() {}

   private void cleanConfig(boolean paramBoolean) {
      if(paramBoolean) {
         ;
      }

      String str = "config.laser.";
      String[] arrayOfString = (String[])((String[])config.getCategoryNames().toArray(new String[config.getCategoryNames().size()]));

      for(int i = 0; i < arrayOfString.length; ++i) {
         config.getCategory(arrayOfString[i]).setLanguageKey(str + arrayOfString[i]).setRequiresMcRestart(true);
      }

   }

   public static int getNextEnchantmentID() {
      for(int i = 0; i < Enchantment.enchantmentsList.length; ++i) {
         if(Enchantment.enchantmentsList[i] == null) {
            return i;
         }
      }

      return -1;
   }

}

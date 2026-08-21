package com.leon.saintsdragons.client.model.block;

import com.leon.saintsdragons.client.renderer.block.DraconianNucleusRenderer;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.resources.Identifier;

public class DraconianNucleusModel extends Model<DraconianNucleusRenderer.RenderState> {
    public static final ModelLayerLocation LAYER_LOCATION =
            new ModelLayerLocation(Identifier.fromNamespaceAndPath("saintsdragons", "draconian_nucleus"), "main");

    public DraconianNucleusModel(ModelPart bakedRoot) {
        super(bakedRoot.getChild("root"), RenderTypes::entityTranslucent);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition modelRoot = mesh.getRoot();
        PartDefinition root = modelRoot.addOrReplaceChild("root", CubeListBuilder.create(),
                PartPose.offset(0.0F, 16.0F, 0.0F));
        PartDefinition group = root.addOrReplaceChild("group", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition outer = group.addOrReplaceChild("outerlayer",
                CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-6.0F, -6.0F, -6.0F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.ZERO);
        outer.addOrReplaceChild("innerlayer",
                CubeListBuilder.create().texOffs(0, 24)
                        .addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.ZERO);
        return LayerDefinition.create(mesh, 48, 48);
    }

    @Override
    public void setupAnim(DraconianNucleusRenderer.RenderState state) {
        resetPose();
        AnimationDefinition animation = state.active
                ? DraconianNucleusAnimations.SPAWN
                : DraconianNucleusAnimations.IDLE;
        animation.bake(root()).apply(state.animationTimeMillis, 1.0F);
    }
}

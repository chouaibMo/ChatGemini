package presentation.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.preat.peekaboo.image.picker.toImageBitmap
import domain.model.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val rotationValue = 45f

@Composable
fun MessageImagesStack(
    message: Message,
    modifier: Modifier = Modifier,
) {
    var visibility by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        visibility = true
    }
    AnimatedVisibility(
        visible = visibility,
        enter = slideInHorizontally()
                + expandHorizontally(expandFrom = Alignment.Start)
                + scaleIn(transformOrigin = TransformOrigin(0.5f, 0f))
                + fadeIn(initialAlpha = 0.3f),
    ) {
        Box(
            contentAlignment = if (!message.isBotMessage) Alignment.CenterEnd else Alignment.CenterStart,
            modifier = modifier
                .padding(
                    start = if (message.isBotMessage) 0.dp else 50.dp,
                    end = if (message.isBotMessage) 50.dp else 0.dp,
                )
                .fillMaxWidth()
        ) {
            CardStack(
                cardCount = message.images.size,
                cardShape = RoundedCornerShape(20.dp),
                cardContent = { index ->
                    Image(
                        bitmap = message.images[index].toImageBitmap(),
                        contentDescription = "Same Card Type with Different Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .heightIn(100.dp, 300.dp)
                            .widthIn(50.dp, 200.dp)
                    )
                },
                orientation = Orientation.Horizontal(
                    alignment = HorizontalAlignment.EndToStart,
                    animationStyle = HorizontalAnimationStyle.FromTop
                ),
            )
        }
    }
}

@Composable
fun CardStack(
    cardContent: @Composable (Int) -> Unit,
    cardCount: Int,
    cardElevation: Dp = 3.dp,
    paddingBetweenCards: Dp = 15.dp,
    animationDuration: Int = 200,
    cardShape: Shape = MaterialTheme.shapes.medium,
    cardBorder: BorderStroke? = null,
    onCardClick: ((Int) -> Unit)? = null,
    orientation: Orientation = Orientation.Vertical()
) {

    val runAnimations = animationDuration > 0
    val coroutineScope = rememberCoroutineScope()

    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    val contentAlignment = getContentAlignment(orientation)

    val rotationValue = getRotation(orientation)

    Box(contentAlignment = contentAlignment) {
        (0 until cardCount).forEachIndexed { index, _ ->
            ShowCard(
                coroutineScope,
                runAnimations,
                selectedIndex,
                index,
                cardCount,
                paddingBetweenCards,
                animationDuration,
                rotationValue,
                orientation,
                cardElevation,
                cardShape,
                cardBorder,
                onCardClick,
                { cardContent(index) },
                { selectedIndex = it })
        }
    }
}

@Composable
private fun ShowCard(
    coroutineScope: CoroutineScope,
    runAnimations: Boolean,
    selectedIndex: Int,
    index: Int,
    cardCount: Int,
    paddingBetweenCards: Dp,
    animationDuration: Int,
    rotationValue: Float,
    orientation: Orientation,
    cardElevation: Dp,
    cardShape: Shape,
    cardBorder: BorderStroke?,
    onCardClick: ((Int) -> Unit)? = null,
    composable: @Composable (Int) -> Unit,
    newIndexBlock: (Int) -> Unit
) {
    var itemPxSize = 0

    val padding = when {
        selectedIndex == index -> 0.dp
        selectedIndex < index -> ((index - selectedIndex) * paddingBetweenCards.value).dp
        selectedIndex > index -> ((cardCount - selectedIndex + index) * paddingBetweenCards.value).dp
        else -> throw IllegalStateException()
    }

    val paddingAnimation by animateDpAsState(padding, tween(animationDuration, easing = FastOutSlowInEasing))
    val offsetAnimation = remember { Animatable(0f) }
    val rotateAnimation = remember { Animatable(0f) }

    val offsetValues = when (orientation) {
        is Orientation.Vertical -> {
            IntOffset(
                if (orientation.animationStyle == VerticalAnimationStyle.ToRight)
                    offsetAnimation.value.toInt()
                else
                    -offsetAnimation.value.toInt(), 0
            )
        }
        is Orientation.Horizontal -> {
            IntOffset(
                0, if (orientation.animationStyle == HorizontalAnimationStyle.FromTop)
                    -offsetAnimation.value.toInt()
                else
                    offsetAnimation.value.toInt()
            )
        }
    }

    val paddingModifier = when {
        orientation is Orientation.Vertical && orientation.alignment == VerticalAlignment.TopToBottom -> PaddingValues(top = paddingAnimation)
        orientation is Orientation.Vertical && orientation.alignment == VerticalAlignment.BottomToTop -> PaddingValues(bottom = paddingAnimation)
        orientation is Orientation.Horizontal && orientation.alignment == HorizontalAlignment.StartToEnd -> PaddingValues(start = paddingAnimation)
        else -> PaddingValues(end = paddingAnimation)
    }

    val modifier = Modifier
        .padding(paddingModifier)
        .zIndex(-padding.value)
        .offset { offsetValues }
        .rotate(rotateAnimation.value)
        .onSizeChanged {
            itemPxSize = if (orientation is Orientation.Vertical) {
                if (itemPxSize > it.width)
                    itemPxSize
                else
                    it.width
            } else {
                if (itemPxSize > it.height)
                    itemPxSize
                else
                    it.height
            }
        }

    Card(
        elevation = CardDefaults.cardElevation(cardElevation),
        shape = cardShape,
        border = cardBorder,
        modifier = modifier.clickable {
            if(cardCount > 1 && selectedIndex == index) {
                onCardClick?.invoke(index)
                animateOnClick(coroutineScope, itemPxSize, runAnimations, animationDuration, rotationValue, index, cardCount, offsetAnimation, rotateAnimation, newIndexBlock)
            }
        }

    ) {
        composable.invoke(index)
    }
}

private fun animateOnClick(
    coroutineScope: CoroutineScope,
    pxValue: Int,
    runAnimations: Boolean,
    animationDuration: Int,
    rotationValue: Float,
    index: Int,
    cardCount: Int,
    offsetAnimation: Animatable<Float, AnimationVector1D>,
    rotateAnimation: Animatable<Float, AnimationVector1D>,
    newIndexBlock: (Int) -> Unit
) {
    val spec: TweenSpec<Float> = tween(animationDuration, easing = FastOutLinearInEasing)

    coroutineScope.launch {
        if (runAnimations)
            offsetAnimation.animateTo(pxValue.toFloat(), spec)

        val newIndex = if (cardCount > index + 1)
            index + 1
        else
            0

        newIndexBlock.invoke(newIndex)

        if(runAnimations) {
            rotateAnimation.animateTo(rotationValue, spec)
            launch { rotateAnimation.animateTo(0f, spec) }
            launch { offsetAnimation.animateTo(0f, spec) }
        }
    }
}


sealed class Orientation {
    data class Vertical(val alignment: VerticalAlignment = VerticalAlignment.TopToBottom,
                        val animationStyle: VerticalAnimationStyle = VerticalAnimationStyle.ToRight
    ) : Orientation()

    data class Horizontal(val alignment: HorizontalAlignment = HorizontalAlignment.StartToEnd,
                          val animationStyle: HorizontalAnimationStyle = HorizontalAnimationStyle.FromTop
    ) : Orientation()
}

enum class VerticalAlignment {
    TopToBottom,
    BottomToTop,
}

enum class HorizontalAlignment {
    StartToEnd,
    EndToStart,
}

enum class VerticalAnimationStyle {
    ToRight,
    ToLeft,
}

enum class HorizontalAnimationStyle {
    FromTop,
    FromBottom
}

private fun getContentAlignment(orientation: Orientation): Alignment {
    return when(orientation) {
        is Orientation.Vertical -> {
            if(orientation.alignment == VerticalAlignment.TopToBottom)
                Alignment.TopCenter
            else
                Alignment.BottomCenter
        }

        is Orientation.Horizontal -> {
            if(orientation.alignment == HorizontalAlignment.StartToEnd)
                Alignment.CenterStart
            else
                Alignment.CenterEnd
        }
    }
}

private fun getRotation(orientation: Orientation): Float {
    return when(orientation) {
        is Orientation.Vertical -> if(orientation.animationStyle == VerticalAnimationStyle.ToRight)
            rotationValue
        else
            -rotationValue
        is Orientation.Horizontal -> if(orientation.animationStyle == HorizontalAnimationStyle.FromTop)
            -rotationValue
        else
            rotationValue
    }
}




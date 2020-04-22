var World = {
    loaded: false,
    rotating: false,
    resourcesLoaded: false,

    model_previousDragValue: { x: 0, y: 0 },
    model_previousScaleValue: 0,
    model_previousRotationValue: 0,


    init: function initFn() {
        this.createOverlays();
    },

    createOverlays: function createOverlaysFn() {

        this.model1 = new AR.Model("assets/onebhk.wt3", {

            scale: {
                x: 0.0075,
                y: 0.0075,
                z: 0.0075
            },

             translate: {
                 x: 1.5,
                 y: -1.5,
                 z: 0.0
             },

            rotate: {

            },
              onScaleBegan: function(scale) {
                            return true;
                        },
                        onScaleChanged: function(scale) {
                            var scaleValue = model_previousScaleValue * scale;
                            this.scale = { x: scaleValue, y: scaleValue, z: scaleValue };

                            return true;
                        },

           onScaleEnded: function(scale) {
                          model_previousScaleValue = this.scale.x;

                          return true;
                      },

                      onDragBegan: function(x, y) {
                          oneFingerGestureAllowed = true;

                          //return true;
                      },
                      onDragChanged: function(x, y) {
                          if (oneFingerGestureAllowed) {
                              this.translate = { x: World.model_previousDragValue.x + x, y: World.model_previousDragValue.y - y };
                          }

                          return true;
                      },
                      onDragEnded: function(x, y) {
                          World.model_previousDragValue.x = this.translate.x;
                          World.model_previousDragValue.y = this.translate.y;

                          return true;
                      },
                      onRotationBegan: function(angleInDegrees) { return true; },
                      onRotationChanged: function(angleInDegrees) {
                          this.rotate.z = World.model_previousRotationValue + angleInDegrees;
                          return true;
                      },
                      onRotationEnded: function(angleInDegrees) {
                          World.model_previousRotationValue = this.rotate.z;
                      }

        });


        this.targetCollectionResource = new AR.TargetCollectionResource("assets/tracker-1.wtc", {
            onLoaded: function() {
                World.resourcesLoaded = true;
                this.loadingStep;
            },
            onError: function(errorMessage) {
                alert(errorMessage);
            }
        });

        this.tracker = new AR.ImageTracker(this.targetCollectionResource, {
            onTargetsLoaded: this.loadingStep,
            onError: function(errorMessage) {
                alert(errorMessage);
            }
        });

        this.trackable = new AR.ImageTrackable(this.tracker, "*", {
            enableExtendedTracking: true,
            drawables: {
                cam: [this.model1]

            },
            onImageRecognized: this.appear,
            onImageLost: this.disappear,
            onError: function(errorMessage) {
                alert(errorMessage);
            }
        });


        this.model1Animation = new AR.ModelAnimation(this.model1, "Take 001");
    },


    appear: function appearFn() {

        World.removeLoadingBar();
        World.model1Animation.start();

    },


    disappear: function disappearFn() {

    },

    removeLoadingBar: function() {

        if (!World.loaded && !World.loaded && World.resourcesLoaded && World.model1.isLoaded()) {
            var e = document.getElementById('loadingMessage');
            e.parentElement.removeChild(e);
            World.loaded = true;
        }
    },


    loadingStep: function loadingStepFn() {
        if (World.resourcesLoaded && World.model1.isLoaded()) {
            var cssDivLeft = " style='display: table-cell;vertical-align: middle; text-align: right; width: 50%; padding-right: 15px;'";
            var cssDivRight = " style='display: table-cell;vertical-align: middle; text-align: left;'";

        }
    }

};

World.init();



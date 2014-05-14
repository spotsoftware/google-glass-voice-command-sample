google-glass-voice-command-sample
=================================

This sample codes demonstrates how to implement the voice recognition into a Glassware application.

<h3>Informations</h3>

The sample is a standard GDK's LiveCard, but you can easily move the code related to voice recognition into an Immersion.. It only depends on your needs. 

We followed the instructions given by the stackoverlow user <i>"blueshadow"</i> at <a href="http://stackoverflow.com/questions/21168267/glass-voice-command-nearest-match-from-given-list">this link</a>.

<h3>Instructions</h3>

The only thing that changes from the given instructions is the path from where you should pull the GlassVoice.apk: from XE17 update it turned out to be "./system/priv-app/GlassVoice.apk".

To convert the pulled .apk into a .jar file you have to download the <a href="https://code.google.com/p/dex2jar/">dex2jar tool</a> (we used the 0.0.9.15 version) and run from the command pront - or shell, depending on what OS you are working on - the following istructions (<a href="https://code.google.com/p/dex2jar/wiki/ModifyApkWithDexTool">more info at this link</a>):
<ul>
  <li>cd [dex2jar_folder_path]</li>
  <li>d2j-dex2jar.sh -f -o [jar_file_name].jar [apk_file_full_path].apk</li>
</ul>

<h3>Notes</h3>
Unfortunatelly the voice recognition isn't working after XE17 update. An issue has been opened <a href="https://code.google.com/p/google-glass-api/issues/detail?can=2&start=0&num=100&q=&colspec=ID%20Type%20Status%20Priority%20Owner%20Component%20Summary&groupby=&sort=&id=511">here</a> and we are waiting for it to be closed.
